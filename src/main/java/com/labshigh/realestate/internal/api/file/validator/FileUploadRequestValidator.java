package com.labshigh.realestate.internal.api.file.validator;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.file.model.request.FileUploadRequestModel;
import lombok.Builder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Builder
public class FileUploadRequestValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return FileUploadRequestModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    FileUploadRequestModel requestModel = (FileUploadRequestModel) target;

    if (requestModel.getFile() == null) {
      errors.reject("multipartFile.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "multipartFile"));
    }

    if (requestModel.getFileType() == null) {
      errors.reject("fileType.required",
          String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "fileType"));
    }

  }
}
