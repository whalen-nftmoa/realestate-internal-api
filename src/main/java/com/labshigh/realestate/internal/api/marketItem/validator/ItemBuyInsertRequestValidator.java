package com.labshigh.realestate.internal.api.marketItem.validator;


import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyInsertRequestModel requestModel = (ItemBuyInsertRequestModel) target;

    if (requestModel.getMarketItemUid() < 1) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (requestModel.getQuantity() < 1) {
      errors.reject("quantity.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "quantity"));
    }

    if (requestModel.getPrice().compareTo(BigDecimal.ONE) < 0) {
      errors.reject("price.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
  }
}
