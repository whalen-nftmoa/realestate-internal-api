package com.labshigh.realestate.internal.api.member.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MemberSendVerifyEmailRequestValidator implements Validator {

  private final String REGEX_EMAIL = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

  @Override
  public boolean supports(Class<?> clazz) {
    return MemberSendVerifyEmailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MemberSendVerifyEmailRequestModel requestModel = (MemberSendVerifyEmailRequestModel) target;
    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (StringUtils.isEmpty(requestModel.getEmail())) {
      errors.reject("email.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "email"));
    }

    Pattern pattern = Pattern.compile(REGEX_EMAIL);
    Matcher matcher = pattern.matcher(requestModel.getEmail());

    if (!matcher.find()) {
      errors.reject("email.notMatched", Constants.MSG_NOT_MATCH_EMAIL);
    }

  }
}
