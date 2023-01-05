package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemHistoryListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemHistoryListRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemHistoryListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemHistoryListRequestModel requestModel = (MarketItemHistoryListRequestModel) target;

    if (requestModel.getMarketItemUid() <= 0) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (requestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (requestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (requestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
    }
  }
}
