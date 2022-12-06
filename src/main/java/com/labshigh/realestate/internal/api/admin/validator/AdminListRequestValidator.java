package com.labshigh.realestate.internal.api.admin.validator;

import com.labshigh.realestate.internal.api.admin.model.request.AdminListRequestModel;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class AdminListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AdminListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AdminListRequestModel adminListRequestModel = (AdminListRequestModel) target;

    if (adminListRequestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }
    if (adminListRequestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }
    if (adminListRequestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.required", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
    }
  }
}
