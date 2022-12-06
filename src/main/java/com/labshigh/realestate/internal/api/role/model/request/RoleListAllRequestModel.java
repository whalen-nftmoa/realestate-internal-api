package com.labshigh.realestate.internal.api.role.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleListAllRequestModel {

  @ApiModelProperty(value = "사용여부")
  private Boolean usedFlag;
}
