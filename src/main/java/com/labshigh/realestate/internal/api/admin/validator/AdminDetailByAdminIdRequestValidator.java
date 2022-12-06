package com.labshigh.realestate.internal.api.admin.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.admin.model.request.AdminDetailByAdminIdRequestModel;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminDetailByAdminIdRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminDetailByAdminIdRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminDetailByAdminIdRequestModel adminDetailByAdminIdRequestModel = (AdminDetailByAdminIdRequestModel) target;

    if (StringUtils.isEmpty(adminDetailByAdminIdRequestModel.getAdminId())) {
      errors.reject("adminId.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "adminId"));
    }
  }
}
