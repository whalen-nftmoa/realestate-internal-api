package com.labshigh.realestate.internal.api.exchange.mapper;

import com.labshigh.realestate.internal.api.exchange.dao.ExchangeVirtualDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExchangeVirtualMapper {

  ExchangeVirtualDao get(ExchangeVirtualDao exchangeVirtualDao);

}
