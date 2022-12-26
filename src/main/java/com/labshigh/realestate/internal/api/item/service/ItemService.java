package com.labshigh.realestate.internal.api.item.service;

import com.labshigh.realestate.core.models.ResponseListModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.common.utils.FileUploadUtils;
import com.labshigh.realestate.internal.api.common.utils.enums.FileType;
import com.labshigh.realestate.internal.api.item.dao.ItemDao;
import com.labshigh.realestate.internal.api.item.dao.ItemFileCommonCodeDao;
import com.labshigh.realestate.internal.api.item.dao.ItemFileDao;
import com.labshigh.realestate.internal.api.item.mapper.ItemFileMapper;
import com.labshigh.realestate.internal.api.item.mapper.ItemMapper;
import com.labshigh.realestate.internal.api.item.model.request.ItemFileInsertRequestModel;
import com.labshigh.realestate.internal.api.item.model.request.ItemInsertRequestModel;
import com.labshigh.realestate.internal.api.item.model.request.ItemListRequestModel;
import com.labshigh.realestate.internal.api.item.model.request.MarketItemInsertRequestModel;
import com.labshigh.realestate.internal.api.item.model.response.ItemDetailResponseModel;
import com.labshigh.realestate.internal.api.item.model.response.ItemListResponseModel;
import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDetailDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailTableDao;
import com.labshigh.realestate.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListByUidRequestModel;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ItemService {

  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.bucket}")
  private String s3NftBucket;


  @Autowired
  private ItemMapper itemMapper;
  @Autowired
  private ItemFileMapper itemFileMapper;
  @Autowired
  private MarketItemMapper marketItemMapper;

  @Autowired
  private ItemBuyMapper itemBuyMapper;

  @Autowired
  private FileUploadUtils fileUploadUtils;

  public ResponseListModel list(ItemListRequestModel requestModel) {
    ResponseListModel result = new ResponseListModel();

    int totalCount = itemMapper.count(requestModel);

    result.setCurrentPage(requestModel.getPage());
    result.setTotalCount(totalCount);
    result.setPageSize(requestModel.getSize());

    if (totalCount < 1) {
      result.setList(Collections.emptyList());
      return result;
    }

    List<ItemListResponseModel> itemDaoList = itemMapper.list(requestModel).stream()
        .map(this::convertItemListResponseModel).collect(Collectors.toList());

    result.setList(itemDaoList);

    return result;
  }

  @Transactional
  public ItemDetailResponseModel insert(ItemInsertRequestModel requestModel) throws IOException {

    LocalDateTime approvalAt = null;

    if (requestModel.getApprovalAt() != null) {
      approvalAt = requestModel.getApprovalAt().atTime(0, 0, 0);
    }

    ItemDao dao = ItemDao.builder()
        .memberUid(requestModel.getMemberUid())
        .allocationUid(requestModel.getAllocationUid())
        .statusUid(requestModel.getStatusUid())
        .imageUri(
            requestModel.getImageUri())
        .projectName(requestModel.getProjectName())
        .totalPrice(requestModel.getTotalPrice())
        .quantity(requestModel.getQuantity())
        .currentQuantity(requestModel.getQuantity())
        .allocationDay(requestModel.getAllocationDay())
        .right(requestModel.getRight())
        .location(requestModel.getLocation())
        .coreValue(requestModel.getCoreValue())
        .area(requestModel.getArea())
        .scale(requestModel.getScale())
        .purpose(requestModel.getPurpose())
        .companyName(requestModel.getCompanyName())
        .approvalAt(approvalAt)
        .websiteUri(requestModel.getWebsiteUri())
        .detail(requestModel.getDetail())
        .itemFiles(requestModel.getItemFiles())
        .build();

    fileCategoryNameBind(dao.getItemFiles());

    String pathName = requestModel.getImageUri()
        .replace("https://" + s3EndPoint + "/" + s3NftBucket, "").split("/")[2];
    //metadata.Json 업로드
    String tokenUri = fileUploadUtils.uploadByObject(dao, pathName, FileType.nft);

    dao.setTokenUri(tokenUri.replace("https://" + s3EndPoint + "/" + s3NftBucket, ""));

    dao.setImageUri(
        requestModel.getImageUri().replace("https://" + s3EndPoint + "/" + s3NftBucket, ""));

    itemMapper.insert(dao);

    for (ItemFileInsertRequestModel itemFileInsertRequestModel : requestModel.getItemFiles()) {
      itemFileMapper.insert(ItemFileDao.builder()
          .categoryUid(itemFileInsertRequestModel.getCategoryUid())
          .itemUid(dao.getUid())
          .fileUri(itemFileInsertRequestModel.getFileUri()
              .replace("https://" + s3EndPoint + "/" + s3NftBucket, ""))
          .build()
      );
    }
    return convertItemDetailResponseModel(dao);
  }

  @Transactional
  public MarketItemDao insertResellMarketItem(MarketItemInsertRequestModel requestModel) {
    List<ItemBuyDetailDao> itemBuyDetailDaoList = itemBuyMapper.listByUid(
        ItemBuyListByUidRequestModel.builder()
            .itemBuyUidList(requestModel.getItemBuyUidList())
            .marketItemUid(requestModel.getMarketItemUid())
            .build());

    if (itemBuyDetailDaoList.size() != requestModel.getItemBuyUidList().size()) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (itemBuyDetailDaoList.stream()
        .anyMatch(v -> v.getMemberUid() != requestModel.getMemberUid())) {
      throw new ServiceException(Constants.MSG_ITEM_BUY_MEMBER_ERROR);
    }

    MarketItemDao marketItemDao = MarketItemDao.builder()
        .itemUid(0) // 재판매 시 여러 아이템이 하나의 판매로 되기 때문에 바라보는 아이템이 없다.
        .quantity(itemBuyDetailDaoList.size())
        .currentQuantity(itemBuyDetailDaoList.size())
        .price(requestModel.getPrice())
        .transactionHash(requestModel.getTransactionHash())
        .build();

    marketItemMapper.insert(marketItemDao);

    for (ItemBuyDetailDao dao : itemBuyDetailDaoList) {
      //마켓아이템 디테일 테이블에 데이터 인서트
      marketItemMapper.insertMarketItemDetail(MarketItemDetailTableDao.builder()
          .itemBuyUid(dao.getUid())
          .marketItemUid(marketItemDao.getUid())
          .build());

      //판매 한 아이템들의 구매아이템 수량을 차감한다.
      itemMapper.updateItemCurrentQuantity(ItemDao.builder()
          .uid(dao.getItemUid())
          .currentQuantity(-1)
          .build());
    }
    return marketItemDao;
  }

  @Transactional
  public MarketItemDao insertMarketItem(MarketItemInsertRequestModel requestModel) {

    ItemDao dao = itemMapper.detail(ItemDao.builder().uid(requestModel.getItemUid()).build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (dao.getMemberUid() != requestModel.getMemberUid()) {
      throw new ServiceException(Constants.MSG_ITEM_MEMBER_ERROR);
    }

    LocalDateTime startAt = null;
    LocalDateTime endAt = null;

    if (requestModel.getStartAt() != null && requestModel.getEndAt() != null) {
      startAt = requestModel.getStartAt().atTime(0, 0, 0);
      endAt = requestModel.getEndAt().atTime(23, 59, 59);
    }

    MarketItemDao marketItemDao = MarketItemDao.builder()
        .itemUid(dao.getUid())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getQuantity())
        .startAt(startAt)
        .endAt(endAt)
        .price(requestModel.getPrice())
        .transactionHash(requestModel.getTransactionHash())
        .build();

    marketItemMapper.insert(marketItemDao);
    return marketItemDao;
  }


  private ItemListResponseModel convertItemListResponseModel(ItemDao dao) {
    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return ItemListResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .memberUid(dao.getMemberUid())
        .allocationUid(dao.getAllocationUid())
        .allocationName(dao.getAllocationName())
        .statusUid(dao.getStatusUid())
        .statusName(dao.getStatusName())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .projectName(dao.getProjectName())
        .totalPrice(dao.getTotalPrice())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
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
        .build();
  }

  private void fileCategoryNameBind(List<ItemFileInsertRequestModel> fileRequestModel) {

    List<ItemFileCommonCodeDao> commonCodes = itemMapper.listCommonCode(
        ItemFileCommonCodeDao.builder()
            .uid(12L)
            .build());
    HashMap<Long, String> categoryMap = new HashMap<>();
    for (ItemFileCommonCodeDao dao : commonCodes) {
      categoryMap.put(dao.getUid(), dao.getName());
    }
    for (ItemFileInsertRequestModel fileModel : fileRequestModel) {

      fileModel.setCategoryName(categoryMap.get(fileModel.getCategoryUid()));
    }
  }

  private ItemDetailResponseModel convertItemDetailResponseModel(ItemDao dao) {
    if (dao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    return ItemDetailResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.getUsedFlag())
        .memberUid(dao.getMemberUid())
        .allocationUid(dao.getAllocationUid())
        .allocationName(dao.getAllocationName())
        .statusUid(dao.getStatusUid())
        .statusName(dao.getStatusName())
        .imageUri("https://" + s3EndPoint + "/" + s3NftBucket + dao.getImageUri())
        .projectName(dao.getProjectName())
        .totalPrice(dao.getTotalPrice())
        .quantity(dao.getQuantity())
        .currentQuantity(dao.getCurrentQuantity())
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
        .build();
  }

}
