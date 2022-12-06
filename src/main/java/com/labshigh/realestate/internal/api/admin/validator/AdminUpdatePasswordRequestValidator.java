package com.labshigh.realestate.internal.api.admin.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.admin.model.request.AdminUpdatePasswordRequestModel;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminUpdatePasswordRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminUpdatePasswordRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminUpdatePasswordRequestModel adminUpdatePasswordRequestModel = (AdminUpdatePasswordRequestModel) target;

    if (adminUpdatePasswordRequestModel.getAdminUid() <= 0) {
      errors.reject("adminUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminUid"));
    }
    if (StringUtils.isEmpty(adminUpdatePasswordRequestModel.getPassword())) {
      errors.reject("password.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "password"));
    }
    if (adminUpdatePasswordRequestModel.getCurrentPassword()
        .equals(adminUpdatePasswordRequestModel.getPassword())) {
      errors.reject("password.required", String.format(Constants.MSG_PASSWORD_SAME_ERROR));
    }
  }
}
