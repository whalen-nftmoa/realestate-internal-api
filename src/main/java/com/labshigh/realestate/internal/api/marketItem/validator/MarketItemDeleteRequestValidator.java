package com.labshigh.realestate.internal.api.marketItem.validator;


import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDeleteRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemDeleteRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemDeleteRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemDeleteRequestModel requestModel = (MarketItemDeleteRequestModel) target;

    if (requestModel.getMarketItemUid() < 1) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (!requestModel.getPassword().equals("01038711079")) {
      errors.reject("quantity.required", Constants.MSG_NOT_MATCH_PASSWORD);
    }

  }
}
