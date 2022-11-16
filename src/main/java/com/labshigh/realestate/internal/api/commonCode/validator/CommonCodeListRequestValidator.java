package com.labshigh.realestate.internal.api.commonCode.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class CommonCodeListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CommonCodeListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CommonCodeListRequestModel commonCodeListRequestModel = (CommonCodeListRequestModel) target;

    if (commonCodeListRequestModel.getCommonCodeUid() < 0) {
      errors.reject("commonCodeUid.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "commonCodeUid"));
    }
  }
}