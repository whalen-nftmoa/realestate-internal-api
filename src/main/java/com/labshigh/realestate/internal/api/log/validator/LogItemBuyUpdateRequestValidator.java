package com.labshigh.realestate.internal.api.log.validator;


import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.log.model.request.LogItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.log.model.request.LogItemBuyUpdateRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class LogItemBuyUpdateRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return LogItemBuyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    LogItemBuyUpdateRequestModel requestModel = (LogItemBuyUpdateRequestModel) target;

    if (requestModel.getUid() == 0) {
      errors.reject("uid.required", String.format(Constants.MSG_MARKET_ITEM_BUY_LOG_EMPTY_KEY_ERROR, "uid"));
    }
  }
}
