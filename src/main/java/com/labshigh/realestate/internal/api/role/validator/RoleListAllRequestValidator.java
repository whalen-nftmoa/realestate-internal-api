package com.labshigh.realestate.internal.api.role.validator;

import com.labshigh.realestate.internal.api.role.model.request.RoleListAllRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class RoleListAllRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return RoleListAllRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    RoleListAllRequestModel roleListAllRequestModel = (RoleListAllRequestModel) target;
  }
}
