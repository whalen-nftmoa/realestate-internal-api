package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListByUidRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyListByUidRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyListByUidRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyListByUidRequestModel requestModel = (ItemBuyListByUidRequestModel) target;

    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }

    if (requestModel.getMarketItemUid() <= 0) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

  }
}
