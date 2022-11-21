package com.labshigh.realestate.internal.api.marketItem.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.item.dao.ItemDao;
import com.labshigh.realestate.internal.api.item.mapper.ItemMapper;
import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.mapper.ItemBuyMapper;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.response.ItemBuyResponseModel;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MarketItemService {

  @Autowired
  private MarketItemMapper marketItemMapper;
  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private ItemBuyMapper itemBuyMapper;

  @Transactional
  public ItemBuyResponseModel insertItemBuy(ItemBuyInsertRequestModel requestModel) {
    MarketItemDetailDao marketItemDetailDao = marketItemMapper.detail(MarketItemDao.builder()
        .uid(requestModel.getMarketItemUid())
        .build());

    if (marketItemDetailDao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (marketItemDetailDao.getCurrentQuantity() - 1 < 0) {
      throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
    }

    if (marketItemDetailDao.getMintingStatus() != 1) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_BUY_MINTING_STATUS_ERROR);
    }
/*
    if (marketItemDetailDao.getMemberUid() == requestModel.getMemberUid()) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_MY_ITEM_BUY_ERROR);
    }*/

    LocalDateTime now = LocalDateTime.now();

    if (!(now.isAfter(marketItemDetailDao.getStartAt()) && (
        now.isBefore(marketItemDetailDao.getEndAt())
            || marketItemDetailDao.getEndAt().equals(now)))) {
      throw new ServiceException(Constants.MSG_MARKET_ITEM_END_DATE_BUY_ERROR);
    }

    ItemDao itemDao = ItemDao.builder()
        .uid(marketItemDetailDao.getItemUid())
        .memberUid(requestModel.getMemberUid())
        .quantity(requestModel.getQuantity())
        .currentQuantity(requestModel.getCurrentQuantity())
        .build();

    itemMapper.insertBuyItem(itemDao);

    MarketItemDao marketItemDao = MarketItemDao.builder()
        .currentQuantity(requestModel.getCurrentQuantity())
        .uid(requestModel.getMarketItemUid())
        .build();
    marketItemMapper.updateCurrentQuantity(marketItemDao);

    ItemBuyDao itemBuyDao = ItemBuyDao.builder()
        .price(requestModel.getPrice())
        .nftId(requestModel.getNftId())
        .contractAddress(requestModel.getContractAddress())
        .marketItemUid(requestModel.getMarketItemUid())
        .itemUid(itemDao.getUid())
        .index(marketItemDetailDao.getQuantity() - (marketItemDetailDao.getCurrentQuantity()
            - requestModel.getQuantity()))
        .build();
    itemBuyMapper.insert(itemBuyDao);

    return convertItemBuyResponseModel(itemBuyDao);
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
