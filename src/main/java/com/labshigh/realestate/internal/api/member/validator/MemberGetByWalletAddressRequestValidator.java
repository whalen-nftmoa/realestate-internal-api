package com.labshigh.realestate.internal.api.member.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.member.model.request.MemberGetByWalletAddressRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberGetByWalletAddressRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberGetByWalletAddressRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberGetByWalletAddressRequestModel requestModel = (MemberGetByWalletAddressRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getWalletAddress())) {
      errors.reject("walletAddress.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
    }
    if (!StringUtils.isEmpty(requestModel.getWalletAddress())
        && requestModel.getWalletAddress().length() != 42) {
      errors.reject("walletAddress.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "walletAddress"));
    }
  }
}
