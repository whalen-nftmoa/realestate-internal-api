package com.labshigh.realestate.internal.api.commonCode.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeInsertRequestModel;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeListRequestModel;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeUpdateRequestModel;
import com.labshigh.realestate.internal.api.commonCode.service.CommonCodeService;
import com.labshigh.realestate.internal.api.commonCode.validator.CommonCodeInsertRequestValidator;
import com.labshigh.realestate.internal.api.commonCode.validator.CommonCodeListRequestValidator;
import com.labshigh.realestate.internal.api.commonCode.validator.CommonCodeUpdateRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/commonCode")
public class CommonCodeController {

  private final CommonCodeService commonCodeService;

  public CommonCodeController(CommonCodeService commonCodeService) {
    this.commonCodeService = commonCodeService;
  }

  @ApiOperation(value = "Common Code 등록")
  @PostMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insert(CommonCodeInsertRequestModel commonCodeInsertRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    CommonCodeInsertRequestValidator.builder().build().validate(
        commonCodeInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        commonCodeService.insert(commonCodeInsertRequestModel);
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

  @ApiOperation(value = "Common Code 수정")
  @PutMapping(value = "/{commonCodeUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> update(@PathVariable(value = "commonCodeUid") long commonCodeUid,
      CommonCodeUpdateRequestModel commonCodeUpdateRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    commonCodeUpdateRequestModel.setCommonCodeUid(commonCodeUid);

    CommonCodeUpdateRequestValidator.builder().build().validate(
        commonCodeUpdateRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        commonCodeService.update(commonCodeUpdateRequestModel);
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

  @ApiOperation(value = "Common Code 삭제")
  @DeleteMapping(value = "/{commonCodeUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> delete(@PathVariable(value = "commonCodeUid") long commonCodeUid) {

    ResponseModel responseModel = new ResponseModel();

    if (commonCodeUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "commonCodeUid"));
    } else {
      try {
        commonCodeService.delete(commonCodeUid);
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

  @ApiOperation(value = "Common Code 리스트")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> list(CommonCodeListRequestModel commonCodeListRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    CommonCodeListRequestValidator.builder().build().validate(
        commonCodeListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(commonCodeService.list(commonCodeListRequestModel));
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

  @ApiOperation(value = "Common Code 상세")
  @GetMapping(value = "/{commonCodeUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detail(@PathVariable(value = "commonCodeUid") long commonCodeUid) {

    ResponseModel responseModel = new ResponseModel();

    if (commonCodeUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "commonCodeUid"));
    } else {
      try {
        responseModel.setData(commonCodeService.detail(commonCodeUid));
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