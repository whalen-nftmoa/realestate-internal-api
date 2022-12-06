package com.labshigh.realestate.internal.api.admin.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.admin.model.request.AdminInsertRequestModel;
import com.labshigh.realestate.internal.api.admin.model.request.AdminSigninRequestModel;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminSigninRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminSigninRequestModel adminSigninRequestModel = (AdminSigninRequestModel) target;

    if (StringUtils.isEmpty(adminSigninRequestModel.getAdminId())) {
      errors.reject("adminId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminId"));
    }
    if (StringUtils.isEmpty(adminSigninRequestModel.getPassword())) {
      errors.reject("password.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "password"));
    }
  }
}
