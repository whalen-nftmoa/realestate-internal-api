package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemHistoryListDao;
import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemResellDao;
import com.labshigh.realestate.internal.api.marketItem.dao.SellMemberDao;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemCancelResellRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemHistoryListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemResellListRequestModel;
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

  List<MarketItemResellDao> listMarketItemResell(MarketItemResellListRequestModel requestModel);

  List<MarketItemResellDao> listMarketItemMyResell(MarketItemResellListRequestModel requestModel);

  List<MarketItemHistoryListDao> listMarketItemHistory(
      MarketItemHistoryListRequestModel requestModel);

  int countMarketItemMyResell(MarketItemCancelResellRequestModel requestModel);

  int countMarketItemHistory(MarketItemHistoryListRequestModel request);

  List<SellMemberDao> listSellMember();

  void updateCurrentQuantity(MarketItemDao dao);

  void updateCancelMarketItem(MarketItemDao dao);

  MarketItemDetailDao detail(MarketItemDao dao);

  void deleteItemBuy(MarketItemDetailDao dao);

  void deleteItemFile(MarketItemDetailDao dao);

  void deleteMarketItem(MarketItemDetailDao dao);

  void deleteItem(MarketItemDetailDao dao);


}
