package com.labshigh.realestate.internal.api.role.mapper;

import com.labshigh.realestate.internal.api.role.dao.RoleDAO;
import com.labshigh.realestate.internal.api.role.model.request.RoleListAllRequestModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleMapper {

  int countAll(@Param(value = "request") RoleListAllRequestModel roleListAllRequestModel);

  List<RoleDAO> listAll(@Param(value = "request") RoleListAllRequestModel roleListAllRequestModel);
}
