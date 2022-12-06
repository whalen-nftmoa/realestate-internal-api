package com.labshigh.realestate.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInsertRequestModel {

  private String adminId;
  private String password;
  private String name;
  private long roleUid;
  private boolean usedFlag;
}
