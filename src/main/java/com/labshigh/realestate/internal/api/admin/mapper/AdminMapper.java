package com.labshigh.realestate.internal.api.admin.mapper;

import com.labshigh.realestate.internal.api.admin.dao.AdminDAO;
import com.labshigh.realestate.internal.api.admin.model.request.AdminListRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AdminMapper {

  int check(long adminUid);

  int count(@Param(value = "request") AdminListRequestModel adminListRequestModel);

  List<AdminDAO> list(@Param(value = "request") AdminListRequestModel adminListRequestModel);

  AdminDAO detail(long adminUid);

  AdminDAO detailByAdminId(String adminId);

  void insert(AdminDAO adminDAO);

  void update(AdminDAO adminDAO);

  void updatePassword(AdminDAO adminDAO);

  void delete(AdminDAO adminDAO);
}
