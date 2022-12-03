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
import com.labshigh.realestate.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemBuyListResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemBuyResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemFileResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemDetailResponseModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.MarketItemListResponseModel;
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

    LocalDateTime now = LocalDateTime.now();

    if (!(now.isAfter(marketItemDetailDao.getStartAt()) && (
        now.isBefore(marketItemDetailDao.getEndAt())
            || marketItemDetailDao.getEndAt().equals(now)))) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_END_DATE_BUY_ERROR);
    }
    for (int idx = 0; idx < requestModel.getQuantity(); idx++) {

      int index =
          marketItemDetailDao.getQuantity() - (marketItemDetailDao.getCurrentQuantity() - (idx
              + 1));

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
        .itemFileUri(dao.getItemFileUri())
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
