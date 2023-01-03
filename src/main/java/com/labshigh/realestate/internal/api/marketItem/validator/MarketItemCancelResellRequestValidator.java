package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemCancelResellRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemCancelResellRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemCancelResellRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemCancelResellRequestModel requestModel = (MarketItemCancelResellRequestModel) target;

    if (requestModel.getMarketItemUid() <= 0) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (requestModel.getCancelTransactionHash() == null || StringUtils.isEmpty(
        requestModel.getCancelTransactionHash().getHash())) {
      errors.reject("cancelTransactionHash.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "cancelTransactionHash"));
    }

  }
}
