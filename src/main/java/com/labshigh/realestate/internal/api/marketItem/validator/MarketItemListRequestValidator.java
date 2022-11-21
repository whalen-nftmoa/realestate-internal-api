package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemListRequestModel marketItem2ListRequestModel = (MarketItemListRequestModel) target;

    if (marketItem2ListRequestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (marketItem2ListRequestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (marketItem2ListRequestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
    }

  }
}
