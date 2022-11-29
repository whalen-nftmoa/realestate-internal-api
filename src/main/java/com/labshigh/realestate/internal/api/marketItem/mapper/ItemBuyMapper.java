package com.labshigh.realestate.internal.api.marketItem.mapper;

import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDao;
import com.labshigh.realestate.internal.api.marketItem.dao.ItemBuyDetailDao;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemBuyMapper {

  void insert(ItemBuyDao dao);

  int count(@Param(value = "request") ItemBuyListRequestModel requestModel);

  List<ItemBuyDetailDao> list(@Param(value = "request") ItemBuyListRequestModel requestModel);

  int countByMember(@Param(value = "request") ItemBuyListRequestModel requestModel);

  List<ItemBuyDetailDao> listByMember(
      @Param(value = "request") ItemBuyListRequestModel requestModel);


}
