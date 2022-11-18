package com.labshigh.realestate.internal.api.item.mapper;

import com.labshigh.realestate.internal.api.item.dao.MarketItemDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemMapper {

  void insert(MarketItemDao dao);
}
