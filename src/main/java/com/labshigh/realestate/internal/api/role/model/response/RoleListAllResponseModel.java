package com.labshigh.realestate.internal.api.role.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleListAllResponseModel {

  private int totalCount;
  private List<RoleResponseModel> list;
}
