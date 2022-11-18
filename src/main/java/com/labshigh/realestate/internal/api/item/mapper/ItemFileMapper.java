package com.labshigh.realestate.internal.api.item.mapper;

import com.labshigh.realestate.internal.api.item.dao.ItemFileDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemFileMapper {

  void insert(ItemFileDao dao);

}
