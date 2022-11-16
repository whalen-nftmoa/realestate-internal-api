package com.labshigh.realestate.internal.api.commonCode.mapper;

import com.labshigh.realestate.internal.api.commonCode.dao.CommonCodeDAO;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CommonCodeMapper {

  int check(long commonCodeUid);
  int checkDuplicate(CommonCodeDAO commonCodeDAO);
  int count(@Param(value = "request") CommonCodeListRequestModel commonCodeListRequestModel);

  List<CommonCodeDAO> list(@Param(value = "request")CommonCodeListRequestModel commonCodeListRequestModel);
  CommonCodeDAO detail(long commonCodeUid);

  void insert(CommonCodeDAO commonCodeDAO);
  void update(CommonCodeDAO commonCodeDAO);
  void updatePhoto(CommonCodeDAO commonCodeDAO);
  void updateUsedFlag(CommonCodeDAO commonCodeDAO);
  void delete(CommonCodeDAO commonCodeDAO);
}