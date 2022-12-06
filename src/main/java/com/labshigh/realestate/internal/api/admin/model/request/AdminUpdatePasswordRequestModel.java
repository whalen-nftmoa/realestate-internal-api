package com.labshigh.realestate.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdatePasswordRequestModel {

  private long adminUid;
  private String currentPassword;
  private String password;
}
