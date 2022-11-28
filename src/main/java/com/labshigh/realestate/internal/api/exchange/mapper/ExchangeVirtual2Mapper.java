package com.labshigh.realestate.internal.api.exchange.mapper;

import com.labshigh.realestate.internal.api.exchange.dao.ExchangeVirtual2Dao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExchangeVirtual2Mapper {

  ExchangeVirtual2Dao get(ExchangeVirtual2Dao exchangeVirtual2Dao);

}
