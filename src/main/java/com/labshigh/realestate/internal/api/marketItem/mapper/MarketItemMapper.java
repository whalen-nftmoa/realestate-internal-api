package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemMapper {

  void insert(MarketItemDao dao);

  MarketItemDetailDao detail(MarketItemDao dao);
}
