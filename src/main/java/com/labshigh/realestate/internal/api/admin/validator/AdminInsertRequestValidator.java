package com.labshigh.realestate.internal.api.admin.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.admin.model.request.AdminInsertRequestModel;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminInsertRequestModel adminInsertRequestModel = (AdminInsertRequestModel) target;

    if (StringUtils.isEmpty(adminInsertRequestModel.getAdminId())) {
      errors.reject("adminId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminId"));
    }
    if (StringUtils.isEmpty(adminInsertRequestModel.getName())) {
      errors.reject("name.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "name"));
    }
    if (StringUtils.isEmpty(adminInsertRequestModel.getPassword())) {
      errors.reject("password.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "password"));
    }
    if (adminInsertRequestModel.getRoleUid() <= 0) {
      errors.reject("roleUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "roleUid"));
    }
  }
}
