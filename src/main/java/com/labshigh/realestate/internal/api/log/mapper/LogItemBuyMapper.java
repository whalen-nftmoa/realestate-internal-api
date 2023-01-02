package com.labshigh.realestate.internal.api.log.mapper;

import com.labshigh.realestate.internal.api.log.dao.LogItemBuyDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LogItemBuyMapper {

  void insert(LogItemBuyDao dao);

  void update(LogItemBuyDao dao);

}
