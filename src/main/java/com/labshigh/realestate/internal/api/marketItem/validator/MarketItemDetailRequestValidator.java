package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemDetailRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemDetailRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemDetailRequestModel marketItem2ListRequestModel = (MarketItemDetailRequestModel) target;

    if (marketItem2ListRequestModel.getMarketItemUid() < 1) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

  }
}
