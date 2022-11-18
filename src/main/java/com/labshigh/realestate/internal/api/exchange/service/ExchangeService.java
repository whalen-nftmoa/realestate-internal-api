package com.labshigh.realestate.internal.api.exchange.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.exchange.dao.ExchangeVirtualDao;
import com.labshigh.realestate.internal.api.exchange.mapper.ExchangeVirtualMapper;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetVirtualRequestModel;
import com.labshigh.realestate.internal.api.exchange.model.response.ExchangeVirtualResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ExchangeService {

  private final ExchangeVirtualMapper exchangeVirtualMapper;

  public ExchangeService(ExchangeVirtualMapper exchangeVirtualMapper) {
    this.exchangeVirtualMapper = exchangeVirtualMapper;
  }

  public ExchangeVirtualResponseModel getVirtual(ExchangeGetVirtualRequestModel requestModel) {

    ExchangeVirtualDao exchangeVirtualDao = exchangeVirtualMapper.get(ExchangeVirtualDao.builder()
        .name(requestModel.getExchangeName())
        .build());

    if (exchangeVirtualDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return ExchangeVirtualResponseModel.builder()
        .uid(exchangeVirtualDao.getUid())
        .createdAt(exchangeVirtualDao.getCreatedAt())
        .updatedAt(exchangeVirtualDao.getUpdatedAt())
        .deletedFlag(exchangeVirtualDao.isDeletedFlag())
        .usedFlag(exchangeVirtualDao.isUsedFlag())
        .name(exchangeVirtualDao.getName())
        .last(exchangeVirtualDao.getLast())
        .lowestAsk(exchangeVirtualDao.getLowestAsk())
        .highestBid(exchangeVirtualDao.getHighestBid())
        .percentChange(exchangeVirtualDao.getPercentChange())
        .baseVolume(exchangeVirtualDao.getBaseVolume())
        .quoteVolume(exchangeVirtualDao.getQuoteVolume())
        .isFrozen(exchangeVirtualDao.getIsFrozen())
        .postOnly(exchangeVirtualDao.getPostOnly())
        .high24hr(exchangeVirtualDao.getHigh24hr())
        .low24hr(exchangeVirtualDao.getLow24hr())
        .build();
  }
}
