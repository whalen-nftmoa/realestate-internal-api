package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemBuyMapper {

  void insert(ItemBuyDao dao);
}
