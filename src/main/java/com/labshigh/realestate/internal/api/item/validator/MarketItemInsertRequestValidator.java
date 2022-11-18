package com.labshigh.realestate.internal.api.item.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.item.model.request.MarketItemInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemInsertRequestModel requestModel = (MarketItemInsertRequestModel) target;

    if (requestModel.getItemUid() <= 0) {
      errors.reject("itemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemUid"));
    }

    if (StringUtils.isEmpty(requestModel.getPrice())
        || Double.parseDouble(requestModel.getPrice()) <= 0D) {
      errors.reject("price.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }

  }
}
