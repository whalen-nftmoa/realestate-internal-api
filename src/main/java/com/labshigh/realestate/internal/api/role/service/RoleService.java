package com.labshigh.realestate.internal.api.role.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.role.dao.RoleDAO;
import com.labshigh.realestate.internal.api.role.mapper.RoleMapper;
import com.labshigh.realestate.internal.api.role.model.request.RoleListAllRequestModel;
import com.labshigh.realestate.internal.api.role.model.response.RoleListAllResponseModel;
import com.labshigh.realestate.internal.api.role.model.response.RoleResponseModel;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RoleService {

  private final RoleMapper roleMapper;

  public RoleService(RoleMapper roleMapper) {
    this.roleMapper = roleMapper;
  }

  public RoleListAllResponseModel listAll(RoleListAllRequestModel roleListAllRequestModel) {

    int totalCount = roleMapper.countAll(roleListAllRequestModel);

    if (totalCount <= 0) {
      return RoleListAllResponseModel.builder()
          .list(Collections.emptyList())
          .totalCount(totalCount)
          .build();
    }

    List<RoleDAO> roleDAOList = roleMapper.listAll(roleListAllRequestModel);

    List<RoleResponseModel> roleResponseModelList = roleDAOList.stream()
        .map(roleDAO -> this._getByDao(roleDAO))
        .collect(Collectors.toList());

    return RoleListAllResponseModel.builder()
        .list(roleResponseModelList)
        .totalCount(totalCount)
        .build();
  }

  private RoleResponseModel _getByDao(RoleDAO roleDAO) {

    if (roleDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return RoleResponseModel.builder()
        .uid(roleDAO.getUid())
        .createdAt(roleDAO.getCreatedAt())
        .updatedAt(roleDAO.getUpdatedAt())
        .deletedFlag(roleDAO.isDeletedFlag())
        .usedFlag(roleDAO.isUsedFlag())
        .name(roleDAO.getName())
        .description(roleDAO.getDescription())
        .build();
  }
}
