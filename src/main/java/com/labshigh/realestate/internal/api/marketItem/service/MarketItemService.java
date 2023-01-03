package com.labshigh.realestate.internal.api.marketItem.service;

import com.labshigh.realestate.core.models.ResponseListModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.item.dao.ItemDao;
import com.labshigh.realestate.internal.api.item.dao.ItemFileDao;
import com.labshigh.realestate.internal.api.item.mapper.ItemFileMapper;
import com.labshigh.realestate.internal.api.item.mapper.ItemMapper;
import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDao;
import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDetailDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailTableDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemResellDao;
import com.labshigh.realestate.internal.api.marketItem.dao.SellMemberDao;
import com.labshigh.realestate.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemDetailMapper;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListByUidRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemRebuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDeleteRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemResellListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemBuyListResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemBuyResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemFileResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemDetailListModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemDetailListResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemDetailResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemListResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemMyResellResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemResellResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.SellMemberResponseModel;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MarketItemService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;

  @Autowired
  private MarketItemMapper marketItemMapper;
  @Autowired
  private MarketItemDetailMapper marketItemDetailMapper;
  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private ItemBuyMapper itemBuyMapper;
  @Autowired
  private ItemFileMapper itemFileMapper;

  @Transactional
  public MarketItemDetailResponseModel insertItemBuy(ItemBuyInsertRequestModel requestModel) {
    MarketItemDetailDao marketItemDetailDao = marketItemMapper.detail(MarketItemDao.builder()
        .uid(requestModel.getMarketItemUid())
        .build());

    if (marketItemDetailDao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (marketItemDetailDao.getCurrentQuantity() - requestModel.getQuantity() < 0) {
      throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
    }

    if (marketItemDetailDao.getMintingStatus() != 1) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_BUY_MINTING_STATUS_ERROR);
    }

    if (marketItemDetailDao.getMemberUid() == requestModel.getMemberUid()) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_MY_ITEM_BUY_ERROR);
    }
    if (marketItemDetailDao.getStartAt() != null && marketItemDetailDao.getEndAt() != null) {
      LocalDateTime now = LocalDateTime.now();

      if (!(now.isAfter(marketItemDetailDao.getStartAt()) && (
          now.isBefore(marketItemDetailDao.getEndAt())
              || marketItemDetailDao.getEndAt().equals(now)))) {
        throw new ServiceException(Constants.MSG_MARKET_ITEM_END_DATE_BUY_ERROR);
      }
    }
    for (long idx = 0; idx < requestModel.getQuantity(); idx++) {

      long index =
          marketItemDetailDao.getQuantity() - (marketItemDetailDao.getCurrentQuantity() - (idx
              + 1L));

      ItemDao itemDao = ItemDao.builder()
          .uid(marketItemDetailDao.getItemUid())
          .memberUid(requestModel.getMemberUid())
          .quantity(1)
          .currentQuantity(1)
          .itemKind(requestModel.getItemKind())
          .index(index)
          .build();

      itemMapper.insertBuyItem(itemDao);

      itemFileMapper.insertBuyItem(ItemFileDao.builder()
          .itemUid(marketItemDetailDao.getItemUid())
          .newItemUid(itemDao.getUid())
          .build());

      ItemBuyDao itemBuyDao = ItemBuyDao.builder()
          .price(requestModel.getPrice())
          .nftId(requestModel.getNftId())
          .contractAddress(requestModel.getContractAddress())
          .marketItemUid(requestModel.getMarketItemUid())
          .itemUid(itemDao.getUid())
          .index(index)
          .transactionHash(requestModel.getTransactionHash().getHash())
          .build();
      itemBuyMapper.insert(itemBuyDao);
    }
    MarketItemDao marketItemDao = MarketItemDao.builder()
        .currentQuantity(requestModel.getCurrentQuantity())
        .uid(requestModel.getMarketItemUid())
        .build();
    marketItemMapper.updateCurrentQuantity(marketItemDao);

    //return convertItemBuyResponseModel(itemBuyDao);

    return convertMarketItemDetailResponseModel(marketItemDetailDao);
  }

  @Transactional
  public void insertItemRebuy(ItemRebuyInsertRequestModel requestModel) {

    MarketItemDetailListRequestModel marketItemDetailListRequestModel = new MarketItemDetailListRequestModel();
    marketItemDetailListRequestModel.setMarketItemUid(requestModel.getMarketItemUid());
    marketItemDetailListRequestModel.setMarketItemDetailUidList(
        requestModel.getMarketItemDetailUidList());

    List<MarketItemDetailTableDao> marketItemDetailTableDaoList = marketItemDetailMapper.list(
        marketItemDetailListRequestModel);

    if (marketItemDetailTableDaoList == null || marketItemDetailTableDaoList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (requestModel.getMarketItemUid() > 0) {
      if (marketItemDetailTableDaoList.get(0).getCurrentQuantity() - 1
          < 0) {
        throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
      }
    } else {
      if (marketItemDetailTableDaoList.get(0).getCurrentQuantity()
          - marketItemDetailTableDaoList.size()
          < 0) {
        throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
      }
    }

    if (marketItemDetailTableDaoList.stream()
        .anyMatch(v -> v.getMemberUid() == requestModel.getMemberUid())) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_MY_ITEM_BUY_ERROR);
    }

    for (MarketItemDetailTableDao detailDao : marketItemDetailTableDaoList) {

      ItemDao itemDao = ItemDao.builder()
          .uid(detailDao.getItemUid())
          .memberUid(requestModel.getMemberUid())
          .quantity(1)
          .currentQuantity(1)
          .itemKind(requestModel.getItemKind())
          .index(detailDao.getIndex())
          .build();

      itemMapper.insertRebuyItem(itemDao);

      itemFileMapper.insertBuyItem(ItemFileDao.builder()
          .itemUid(detailDao.getItemUid())
          .newItemUid(itemDao.getUid())
          .build());

      ItemBuyDao itemBuyDao = ItemBuyDao.builder()
          .price(requestModel.getPrice())
          .nftId(requestModel.getNftId())
          .contractAddress(requestModel.getContractAddress())
          .marketItemUid(detailDao.getMarketItemUid())
          .itemUid(itemDao.getUid())
          .index(itemDao.getIndex())
          .transactionHash(requestModel.getTransactionHash().getHash())
          .build();
      itemBuyMapper.insert(itemBuyDao);

      MarketItemDao marketItemDao = MarketItemDao.builder()
          .currentQuantity(1)
          .uid(detailDao.getMarketItemUid())
          .build();

      marketItemMapper.updateCurrentQuantity(marketItemDao);

      //판매 여부 업데이트
      marketItemDetailMapper.updateSellFlag(MarketItemDetailTableDao.builder()
          .uid(detailDao.getUid())
          .sellFlag(true)
          .build());

    }

  }


  public ResponseListModel listItemBuy(ItemBuyListRequestModel requestModel) {
    ResponseListModel responseListModel = new ResponseListModel();

    int totalCount = itemBuyMapper.count(requestModel);

    responseListModel.setCurrentPage(requestModel.getPage());
    responseListModel.setTotalCount(totalCount);
    responseListModel.setPageSize(requestModel.getSize());
    if (totalCount < 1) {
      responseListModel.setList(Collections.emptyList());
      return responseListModel;
    }

    List<ItemBuyDetailDao> itemBuyDetailDaoList = itemBuyMapper.list(requestModel);

    List<ItemBuyListResponseModel> responseModelList = itemBuyDetailDaoList.stream()
        .map(this::convertItemBuyListResponseModel).collect(Collectors.toList());
    responseListModel.setList(responseModelList);
    return responseListModel;
  }

  public List<ItemBuyListResponseModel> listItemByMember(
      ItemBuyListByUidRequestModel requestModel) {
    List<ItemBuyDetailDao> dao = itemBuyMapper.listByUid(requestModel);

    return dao.stream().map(this::convertItemBuyListResponseModel)
        .collect(Collectors.toList());

  }

  public List<MarketItemResellResponseModel> listMarketItemResell(
      MarketItemResellListRequestModel requestModel) {

    List<MarketItemResellDao> daoList = marketItemMapper.listMarketItemResell(requestModel);

    return daoList.stream().map(this::convertMarketItemResellResponseModel)
        .collect(Collectors.toList());
  }

  public MarketItemMyResellResponseModel listMarketItemMyResell(
      MarketItemResellListRequestModel requestModel) {

    List<MarketItemResellDao> daoList = marketItemMapper.listMarketItemMyResell(requestModel);

    if (daoList.isEmpty()) {
      return MarketItemMyResellResponseModel.builder().itemList(Collections.emptyList()).build();
    }

    List<MarketItemResellResponseModel> responseModelList = daoList.stream()
        .map(this::convertMarketItemResellResponseModel)
        .collect(Collectors.toList());
    long itemUid = responseModelList.get(0).getItemUid();
    List<ItemFileDao> itemFileDaoList = itemFileMapper.listFile(
        ItemFileDao.builder().itemUid(itemUid).build());
    List<ItemFileResponseModel> itemFileList = itemFileDaoList.stream()
        .map(this::convertItemFileResponseModel).collect(Collectors.toList());
    return MarketItemMyResellResponseModel.builder()
        .itemList(responseModelList)
        .itemFileList(itemFileList)
        .build();
  }


  public ResponseListModel listItemBuyByMember(ItemBuyListRequestModel requestModel) {
    ResponseListModel responseListModel = new ResponseListModel();

    int totalCount = itemBuyMapper.countByMember(requestModel);

    responseListModel.setCurrentPage(requestModel.getPage());
    responseListModel.setTotalCount(totalCount);
    responseListModel.setPageSize(requestModel.getSize());
    if (totalCount < 1) {
      responseListModel.setList(Collections.emptyList());
      return responseListModel;
    }

    List<ItemBuyDetailDao> itemBuyDetailDaoList = itemBuyMapper.listByMember(requestModel);

    List<ItemBuyListResponseModel> responseModelList = itemBuyDetailDaoList.stream()
        .map(this::convertItemBuyListResponseModel).collect(Collectors.toList());
    responseListModel.setList(responseModelList);
    return responseListModel;
  }

  public ResponseListModel listMarketItem(MarketItemListRequestModel requestModel) {
    ResponseListModel responseListModel = new ResponseListModel();

    int totalCount = marketItemMapper.countMarketItem(requestModel);

    responseListModel.setCurrentPage(requestModel.getPage());
    responseListModel.setTotalCount(totalCount);
    responseListModel.setPageSize(requestModel.getSize());
    if (totalCount < 1) {
      responseListModel.setList(Collections.emptyList());
      return responseListModel;
    }

    List<MarketItemDetailDao> marketItemDetailDaoList = marketItemMapper.listMarketItem(
        requestModel);

    List<MarketItemListResponseModel> responseModelList = marketItemDetailDaoList.stream()
        .map(this::convertMarketItemResponseModel).collect(Collectors.toList());
    responseListModel.setList(responseModelList);

    return responseListModel;
  }

  public MarketItemDetailResponseModel detailMarketItem(
      MarketItemDetailRequestModel requestModel) {
    MarketItemDetailDao dao = marketItemMapper.detail(
        MarketItemDao.builder().uid(requestModel.getMarketItemUid()).build());

    MarketItemDetailResponseModel responseModel = convertMarketItemDetailResponseModel(dao);

    List<ItemFileDao> fileDaoList = itemFileMapper.listFile(
        ItemFileDao.builder().itemUid(responseModel.getItemUid()).build());

    responseModel.setFileList(
        fileDaoList.stream().map(this::convertItemFileResponseModel).collect(Collectors.toList())
    );
    return responseModel;
  }

  public MarketItemDetailListResponseModel listMarketItemDetail(
      MarketItemDetailListRequestModel requestModel) {

    List<MarketItemDetailTableDao> daoList = marketItemDetailMapper.list(requestModel);

    if (daoList.isEmpty()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    List<MarketItemDetailListModel> detailList = daoList.stream()
        .map(this::convertMarketItemDetailListModel).collect(Collectors.toList());

    List<ItemFileDao> fileDaoList = itemFileMapper.listFile(
        ItemFileDao.builder().itemUid(daoList.get(0).getItemUid()).build());

    return MarketItemDetailListResponseModel.builder().detailList(detailList).fileList(
        fileDaoList.stream().map(this::convertItemFileResponseModel).collect(Collectors.toList())
    ).build();


  }

  public List<SellMemberResponseModel> listSellMember() {
    List<SellMemberDao> sellMemberDaoList = marketItemMapper.listSellMember();

    return sellMemberDaoList.stream().map(this::convertSellMemberResponseModel)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteMarketItem(MarketItemDeleteRequestModel requestModel) {

    MarketItemDetailDao dao = marketItemMapper.detail(
        MarketItemDao.builder().uid(requestModel.getMarketItemUid()).build());

    marketItemMapper.deleteItemBuy(dao);
    marketItemMapper.deleteItemFile(dao);
    marketItemMapper.deleteMarketItem(dao);
    marketItemMapper.deleteItem(dao);


  }

  private MarketItemDetailListModel convertMarketItemDetailListModel(MarketItemDetailTableDao dao) {
    return MarketItemDetailListModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .marketItemUid(dao.getMarketItemUid())
        .itemBuyUid(dao.getItemBuyUid())
        .sellFlag(dao.isSellFlag())
        .itemUid(dao.getItemUid())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .price(dao.getPrice())
        .usdPrice(dao.getUsdPrice())
        .fogPrice(dao.getFogPrice())
        .indexName(dao.getIndexName())
        .build();

  }

  private MarketItemResellResponseModel convertMarketItemResellResponseModel(
      MarketItemResellDao dao) {
    return MarketItemResellResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
        .price(dao.getPrice())
        .usdPrice(dao.getUsdPrice())
        .fogPrice(dao.getFogPrice())
        .transactionHash(dao.getTransactionHash())
        .sellId(dao.getSellId())
        .nftId(dao.getNftId())
        .mintingStatus(dao.getMintingStatus())
        .totalPrice(dao.getTotalPrice())
        .usdTotalPrice(dao.getUsdTotalPrice())
        .fogTotalPrice(dao.getTotalPrice())
        .indexName(dao.getIndexName())
        .walletAddress(dao.getWalletAddress())
        .projectName(dao.getProjectName())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .itemUid(dao.getItemUid())
        .build();
  }

  private SellMemberResponseModel convertSellMemberResponseModel(SellMemberDao dao) {
    return SellMemberResponseModel.builder()
        .memberUid(dao.getMemberUid())
        .walletAddress(dao.getWalletAddress())
        .build();
  }

  private ItemBuyListResponseModel convertItemBuyListResponseModel(ItemBuyDetailDao dao) {

    List<ItemFileDao> fileDaoList = itemFileMapper.listFile(
        ItemFileDao.builder().itemUid(dao.getItemUid()).build());

    return ItemBuyListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .itemUid(dao.getItemUid())
        .marketItemUid(dao.getMarketItemUid())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
        .price(dao.getPrice())
        .usdPrice(dao.getUsdPrice())
        .fogPrice(dao.getFogPrice())
        .contractAddress(dao.getContractAddress())
        .nftId(dao.getNftId())
        .memberUid(dao.getMemberUid())
        .allocationUid(dao.getAllocationUid())
        .statusUid(dao.getStatusUid())
        .allocationName(dao.getAllocationName())
        .statusName(dao.getStatusName())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .projectName(dao.getProjectName())
        .totalPrice(dao.getTotalPrice())
        .usdTotalPrice(dao.getUsdTotalPrice())
        .fogTotalPrice(dao.getFogTotalPrice())
        .allocationDay(dao.getAllocationDay())
        .right(dao.getRight())
        .location(dao.getLocation())
        .coreValue(dao.getCoreValue())
        .area(dao.getArea())
        .scale(dao.getScale())
        .purpose(dao.getPurpose())
        .companyName(dao.getCompanyName())
        .indexName(dao.getIndexName())
        .approvalAt(dao.getApprovalAt())
        .websiteUri(dao.getWebsiteUri())
        .detail(dao.getDetail())
        .tokenUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getTokenUri())
        .walletAddress(dao.getWalletAddress())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .transactionHash(dao.getTransactionHash())
        .fileList(fileDaoList.stream().map(this::convertItemFileResponseModel)
            .collect(Collectors.toList()))
        .build();

  }

  private ItemFileResponseModel convertItemFileResponseModel(ItemFileDao dao) {
    return ItemFileResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .categoryUid(dao.getCategoryUid())
        .itemUid(dao.getItemUid())
        .fileUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getFileUri())
        .build();
  }

  private MarketItemDetailResponseModel convertMarketItemDetailResponseModel(
      MarketItemDetailDao dao) {

    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return MarketItemDetailResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .itemUid(dao.getItemUid())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .price(dao.getPrice())
        .usdPrice(dao.getUsdPrice())
        .fogPrice(dao.getFogPrice())
        .transactionHash(dao.getTransactionHash())
        .sellId(dao.getSellId())
        .nftId(dao.getNftId())
        .mintingStatus(dao.getMintingStatus())
        .memberUid(dao.getMemberUid())
        .allocationUid(dao.getAllocationUid())
        .statusUid(dao.getStatusUid())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .projectName(dao.getProjectName())
        .totalPrice(dao.getTotalPrice())
        .usdTotalPrice(dao.getUsdTotalPrice())
        .fogTotalPrice(dao.getFogTotalPrice())
        .allocationDay(dao.getAllocationDay())
        .right(dao.getRight())
        .location(dao.getLocation())
        .coreValue(dao.getCoreValue())
        .area(dao.getArea())
        .scale(dao.getScale())
        .purpose(dao.getPurpose())
        .companyName(dao.getCompanyName())
        .approvalAt(dao.getApprovalAt())
        .websiteUri(dao.getWebsiteUri())
        .detail(dao.getDetail())
        .tokenUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getTokenUri())
        .walletAddress(dao.getWalletAddress())
        .build();
  }

  private MarketItemListResponseModel convertMarketItemResponseModel(MarketItemDetailDao dao) {
    return MarketItemListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .itemUid(dao.getItemUid())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
        .startAt(dao.getStartAt())
        .endAt(dao.getEndAt())
        .price(dao.getPrice())
        .usdPrice(dao.getUsdPrice())
        .fogPrice(dao.getFogPrice())
        .transactionHash(dao.getTransactionHash())
        .sellId(dao.getSellId())
        .nftId(dao.getNftId())
        .mintingStatus(dao.getMintingStatus())
        .memberUid(dao.getMemberUid())
        .allocationUid(dao.getAllocationUid())
        .statusUid(dao.getStatusUid())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .projectName(dao.getProjectName())
        .totalPrice(dao.getTotalPrice())
        .usdTotalPrice(dao.getUsdTotalPrice())
        .fogTotalPrice(dao.getFogTotalPrice())
        .allocationDay(dao.getAllocationDay())
        .right(dao.getRight())
        .location(dao.getLocation())
        .coreValue(dao.getCoreValue())
        .area(dao.getArea())
        .scale(dao.getScale())
        .purpose(dao.getPurpose())
        .companyName(dao.getCompanyName())
        .approvalAt(dao.getApprovalAt())
        .websiteUri(dao.getWebsiteUri())
        .detail(dao.getDetail())
        .tokenUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getTokenUri())
        .walletAddress(dao.getWalletAddress())
        .itemFileUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getItemFileUri())
        .build();
  }

  private ItemBuyResponseModel convertItemBuyResponseModel(ItemBuyDao dao) {
    return ItemBuyResponseModel.builder()
        .uid(dao.getUid())
        .itemUid(dao.getItemUid())
        .marketItemUid(dao.getMarketItemUid())
        .price(dao.getPrice())
        .nftId(dao.getNftId())
        .contractAddress(dao.getContractAddress())
        .index(dao.getIndex())
        .build();
  }

}
