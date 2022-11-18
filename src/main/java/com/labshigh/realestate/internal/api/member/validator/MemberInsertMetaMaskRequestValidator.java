package com.labshigh.realestate.internal.api.member.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.member.model.request.MemberInsertMetaMaskRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberInsertMetaMaskRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberInsertMetaMaskRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberInsertMetaMaskRequestModel requestModel = (MemberInsertMetaMaskRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getPublicKey())) {
      errors.reject("publicKey.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "publicKey"));
    }
    if (!StringUtils.isEmpty(requestModel.getPublicKey())
        && requestModel.getPublicKey().length() != 42) {
      errors.reject("publicKey.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "publicKey"));
    }
    if (StringUtils.isEmpty(requestModel.getSignature())) {
      errors.reject("signature.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "signature"));
    }
    if (!StringUtils.isEmpty(requestModel.getSignature())
        && requestModel.getSignature().length() != 132) {
      errors.reject("signature.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "signature"));
    }
    if (requestModel.getTimestamp() == null) {
      errors.reject("timestamp.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "timestamp"));
    }
  }
}
