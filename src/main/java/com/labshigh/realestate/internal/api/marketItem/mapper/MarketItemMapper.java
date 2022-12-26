package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailTableDao;
import com.labshigh.realestate.internal.api.marketItem.dao.SellMemberDao;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemMapper {

  void insert(MarketItemDao dao);

  void insertMarketItemDetail(MarketItemDetailTableDao dao);

  int countMarketItem(@Param(value = "request") MarketItemListRequestModel requestModel);

  List<MarketItemDetailDao> listMarketItem(
      @Param(value = "request") MarketItemListRequestModel requestModel);

  List<SellMemberDao> listSellMember();

  void updateCurrentQuantity(MarketItemDao dao);

  void updateCancelMarketItem(MarketItemDao dao);

  MarketItemDetailDao detail(MarketItemDao dao);

  void deleteItemBuy(MarketItemDetailDao dao);

  void deleteItemFile(MarketItemDetailDao dao);

  void deleteMarketItem(MarketItemDetailDao dao);

  void deleteItem(MarketItemDetailDao dao);


}
