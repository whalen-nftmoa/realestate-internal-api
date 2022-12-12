package com.labshigh.realestate.internal.api.marketItem.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemBuyListByMemberRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemBuyListRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemBuyListRequestModel itemBuyListRequestModel = (ItemBuyListRequestModel) target;

//    if (itemBuyListRequestModel.getMemberUid() <= 0) {
//      errors.reject("memberUid.required",
//          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
//    }

    if (itemBuyListRequestModel.getPage() <= 0) {
      errors.reject("page.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "page"));
    }

    if (itemBuyListRequestModel.getSize() <= 0) {
      errors.reject("size.required", String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "size"));
    }

    if (itemBuyListRequestModel.getSize() > Constants.MAX_LIST_PAGE_SIZE) {
      errors.reject("size.lengthOver", String.format(Constants.MSG_MAX_LENGTH_OVER_ERROR, "size"));
    }

  }
}
