package com.labshigh.realestate.internal.api.item.validator;

import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.item.model.request.ItemInsertRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class ItemInsertRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ItemInsertRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ItemInsertRequestModel requestModel = (ItemInsertRequestModel) target;

    if (StringUtils.isEmpty(requestModel.getProjectName())) {
      errors.reject("name.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "projectName"));
    }

    if (requestModel.getAllocationUid() <= 0) {
      errors.reject("AllocationUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "AllocationUid"));
    }
    if (requestModel.getStatusUid() <= 0) {
      errors.reject("statusUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "statusUid"));
    }
    if (requestModel.getMemberUid() <= 0) {
      errors.reject("memberUid.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "memberUid"));
    }
    if (requestModel.getQuantity() <= 0) {
      errors.reject("quantity.required",
          Constants.MSG_ITEM_QUANTITY_ERROR);
    }
    if (StringUtils.isEmpty(requestModel.getTotalPrice())
        || Double.parseDouble(requestModel.getTotalPrice()) <= 0D) {
      errors.reject("totalPrice.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "totalPrice"));
    }
    String imageUri = requestModel.getImageUri();

    if (StringUtils.isEmpty(imageUri)) {
      errors.reject("imageUri.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "imageUri"));
    }

    if (!StringUtils.isEmpty(imageUri) && !(imageUri.startsWith("https://") || imageUri.startsWith(
        "http://"))) {
      errors.reject("imageUri.validate", Constants.MSG_WRONG_URL);
    }
  }
}
