package com.labshigh.realestate.internal.api.item.mapper;

import com.labshigh.realestate.internal.api.item.dao.ItemDao;
import com.labshigh.realestate.internal.api.item.model.request.ItemListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemMapper {

  void insert(ItemDao dao);

  void insertBuyItem(ItemDao dao);

  ItemDao detail(ItemDao dao);

  int count(ItemListRequestModel itemListRequestModel);

  List<ItemDao> list(ItemListRequestModel itemListRequestModel);

}
