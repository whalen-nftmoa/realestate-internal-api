package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.MarketItemDetailTableDao;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MarketItemDetailMapper {

  void insertMarketItemDetail(MarketItemDetailTableDao dao);

  List<MarketItemDetailTableDao> list(MarketItemDetailListRequestModel requestModel);

}
