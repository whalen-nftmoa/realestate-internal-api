package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.ItemFileDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemMapper {

  void insert(MarketItemDao dao);

  int countMarketItem(@Param(value = "request") MarketItemListRequestModel requestModel);

  List<MarketItemDetailDao> listMarketItem(
      @Param(value = "request") MarketItemListRequestModel requestModel);

  void updateCurrentQuantity(MarketItemDao dao);

  MarketItemDetailDao detail(MarketItemDao dao);

  List<ItemFileDao> listFile(ItemFileDao dao);
}
