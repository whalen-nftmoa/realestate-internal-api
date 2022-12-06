package com.labshigh.realestate.internal.api.admin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSigninRequestModel {

  private String adminId;
  private String password;
}
