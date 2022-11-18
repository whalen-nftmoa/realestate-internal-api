package com.labshigh.realestate.internal.api.file.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.file.model.request.FileUploadRequestModel;
import com.labshigh.realestate.internal.api.file.service.FileService;
import com.labshigh.realestate.internal.api.file.validator.FileUploadRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/file")
public class FileController {

  @Autowired
  private FileService fileService;

  @ApiOperation("Item 이미지 업로드")
  @PostMapping(value = "/", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> upload(@RequestBody FileUploadRequestModel fileUploadRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    FileUploadRequestValidator.builder().build()
        .validate(fileUploadRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(fileService.upload(fileUploadRequestModel));
      } catch (ServiceException e) {
        responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        responseModel.setMessage(e.getMessage());
      } catch (Exception e) {
        responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseModel.error.setErrorMessage(e.getLocalizedMessage());
      }
    }
    return responseModel.toResponse();
  }

}
