package com.labshigh.realestate.internal.api.exchange.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetVirtualRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ExchangeGetVirtualRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ExchangeGetVirtualRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ExchangeGetVirtualRequestModel requestModel = (ExchangeGetVirtualRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getExchangeName())) {
      errors.reject("exchangeName.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "exchangeName"));
    }
  }
}
