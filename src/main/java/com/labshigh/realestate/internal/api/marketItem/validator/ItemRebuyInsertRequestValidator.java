package com.labshigh.realestate.internal.api.marketItem.validator;


import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemRebuyInsertRequestModel;
import java.math.BigDecimal;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemRebuyInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemRebuyInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemRebuyInsertRequestModel requestModel = (ItemRebuyInsertRequestModel) target;

    if (requestModel.getMarketItemUid() < 1 && requestModel.getMarketItemDetailUidList()
        .isEmpty()) {
      errors.reject("marketItemUidOrMarketItemDetailUidList.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR,
              "marketItemUidOrMarketItemDetailUidList"));
    }

    if (requestModel.getPrice().compareTo(BigDecimal.ONE) < 0) {
      errors.reject("price.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "price"));
    }
  }
}
