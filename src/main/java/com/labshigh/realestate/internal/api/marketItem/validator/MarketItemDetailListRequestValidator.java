package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemDetailListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemDetailListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemDetailListRequestModel requestModel = (MarketItemDetailListRequestModel) target;

    if (requestModel.getMarketItemUid() <= 0) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

  }
}
