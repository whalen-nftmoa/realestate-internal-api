package com.labshigh.realestate.internal.api.marketItem.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.mapper.MarketItemMapper;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MarketItemService {

  @Autowired
  private MarketItemMapper marketItemMapper;

  @Transactional
  public void insertItemBuy(ItemBuyInsertRequestModel requestModel) {
    MarketItemDetailDao marketItemDetailDao = marketItemMapper.detail(MarketItemDao.builder()
        .uid(requestModel.getMarketItemUid())
        .build());

    if (marketItemDetailDao == null) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    if (marketItemDetailDao.getCurrentQuantity() - 1 < 0) {
      throw new ServiceException(Constants.MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR);
    }

  }

}
