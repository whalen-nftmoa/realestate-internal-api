package com.labshigh.realestate.internal.api.item.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ItemFileMapper {

  void insert(ItemFileDao dao);

}
