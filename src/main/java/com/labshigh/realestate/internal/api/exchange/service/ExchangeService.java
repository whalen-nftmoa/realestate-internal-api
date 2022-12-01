package com.labshigh.realestate.internal.api.exchange.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.exchange.dao.ExchangeDao;
import com.labshigh.realestate.internal.api.exchange.dao.ExchangeVirtual2Dao;
import com.labshigh.realestate.internal.api.exchange.dao.ExchangeVirtualDao;
import com.labshigh.realestate.internal.api.exchange.mapper.ExchangeMapper;
import com.labshigh.realestate.internal.api.exchange.mapper.ExchangeVirtual2Mapper;
import com.labshigh.realestate.internal.api.exchange.mapper.ExchangeVirtualMapper;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetRequestModel;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetVirtualRequestModel;
import com.labshigh.realestate.internal.api.exchange.model.response.ExchangeResponseModel;
import com.labshigh.realestate.internal.api.exchange.model.response.ExchangeVirtualResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ExchangeService {

  private final ExchangeMapper exchangeMapper;
  private final ExchangeVirtualMapper exchangeVirtualMapper;
  private final ExchangeVirtual2Mapper exchangeVirtual2Mapper;

  public ExchangeService(ExchangeMapper exchangeMapper, ExchangeVirtualMapper exchangeVirtualMapper,
      ExchangeVirtual2Mapper exchangeVirtual2Mapper) {
    this.exchangeMapper = exchangeMapper;
    this.exchangeVirtualMapper = exchangeVirtualMapper;
    this.exchangeVirtual2Mapper = exchangeVirtual2Mapper;
  }

  public ExchangeVirtualResponseModel getVirtual(ExchangeGetVirtualRequestModel requestModel) {

    if (requestModel.getExchangeName().equals("FOG_WON") || requestModel.getExchangeName()
        .equals("FoGNET_USD")) {

      ExchangeVirtual2Dao exchangeVirtual2Dao = exchangeVirtual2Mapper.get(
          ExchangeVirtual2Dao.builder().name(requestModel.getExchangeName()).build());

      if (exchangeVirtual2Dao == null) {
        throw new ServiceException(String.format(Constants.MSG_NO_DATA));
      }

      return ExchangeVirtualResponseModel.builder()
          .uid(exchangeVirtual2Dao.getUid())
          .createdAt(exchangeVirtual2Dao.getCreatedAt())
          .updatedAt(exchangeVirtual2Dao.getUpdatedAt())
          .deletedFlag(exchangeVirtual2Dao.isDeletedFlag())
          .usedFlag(exchangeVirtual2Dao.isUsedFlag())
          .name(requestModel.getExchangeName())
          .last(exchangeVirtual2Dao.getPrice())
          .build();
    } else {

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

  public ExchangeResponseModel get(ExchangeGetRequestModel requestModel) {

    ExchangeDao exchangeDao = exchangeMapper.get(ExchangeDao.builder()
        .name(requestModel.getExchangeName())
        .build());

    if (exchangeDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return ExchangeResponseModel.builder()
        .uid(exchangeDao.getUid())
        .createdAt(exchangeDao.getCreatedAt())
        .updatedAt(exchangeDao.getUpdatedAt())
        .deletedFlag(exchangeDao.isDeletedFlag())
        .usedFlag(exchangeDao.isUsedFlag())
        .code(exchangeDao.getCode())
        .currencyCode(exchangeDao.getCurrencyCode())
        .currencyName(exchangeDao.getCurrencyName())
        .country(exchangeDao.getCountry())
        .name(exchangeDao.getName())
        .date(exchangeDao.getDate())
        .time(exchangeDao.getTime())
        .recurrenceCount(exchangeDao.getRecurrenceCount())
        .basePrice(exchangeDao.getBasePrice())
        .openingPrice(exchangeDao.getOpeningPrice())
        .highPrice(exchangeDao.getHighPrice())
        .lowPrice(exchangeDao.getLowPrice())
        .change(exchangeDao.getChange())
        .changePrice(exchangeDao.getChangePrice())
        .cashBuyingPrice(exchangeDao.getCashBuyingPrice())
        .cashSellingPrice(exchangeDao.getCashSellingPrice())
        .ttBuyingPrice(exchangeDao.getTtBuyingPrice())
        .ttSellingPrice(exchangeDao.getTtSellingPrice())
        .tcBuyingPrice(exchangeDao.getTcBuyingPrice())
        .fcSellingPrice(exchangeDao.getFcSellingPrice())
        .exchangeCommission(exchangeDao.getExchangeCommission())
        .usDollarRate(exchangeDao.getUsDollarRate())
        .high52wPrice(exchangeDao.getHigh52wPrice())
        .high52wDate(exchangeDao.getHigh52wDate())
        .low52wPrice(exchangeDao.getLow52wPrice())
        .low52wDate(exchangeDao.getLow52wDate())
        .currencyUnit(exchangeDao.getCurrencyUnit())
        .provider(exchangeDao.getProvider())
        .signedChangePrice(exchangeDao.getSignedChangePrice())
        .signedChangeRate(exchangeDao.getSignedChangeRate())
        .changeRate(exchangeDao.getChangeRate())
        .build();
  }
}
