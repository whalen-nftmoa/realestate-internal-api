package com.labshigh.realestate.internal.api.item.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.item.model.request.MarketItemInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class MarketItemReSellInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return MarketItemInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    MarketItemInsertRequestModel requestModel = (MarketItemInsertRequestModel) target;

    if (requestModel.getMarketItemUid() <= 0) {
      errors.reject("marketItemUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "marketItemUid"));
    }

    if (requestModel.getPrice().compareTo(BigDecimal.ONE) < 0) {
      errors.reject("price.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }

    if (requestModel.getItemBuyUidList() == null || requestModel.getItemBuyUidList().isEmpty()) {
      errors.reject("itemBuyUidList.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "itemBuyUidList"));
    }

  }
}
