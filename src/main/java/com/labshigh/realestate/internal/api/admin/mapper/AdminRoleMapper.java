package com.labshigh.realestate.internal.api.admin.mapper;

import com.labshigh.realestate.internal.api.admin.dao.AdminRoleDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AdminRoleMapper {

  void insert(AdminRoleDAO adminRoleDAO);

  void update(AdminRoleDAO adminRoleDAO);

  void delete(AdminRoleDAO adminRoleDAO);

  void deleteByAdminUid(AdminRoleDAO adminRoleDAO);
}
