package com.labshigh.realestate.internal.api.log.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.log.model.request.*;
import com.labshigh.realestate.internal.api.log.service.LogItemBuyService;
import com.labshigh.realestate.internal.api.log.validator.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/logs")
public class LogController {

  @Autowired
  private LogItemBuyService logItemBuyService;

  @ApiOperation("Item 구매 신규 로그")
  @PostMapping(value = "/itembuy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertLogItemBuy(
      @RequestBody LogItemBuyInsertRequestModel itemBuyInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    LogItemBuyInsertRequestValidator.builder().build()
        .validate(itemBuyInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(logItemBuyService.insertItemBuyLog(itemBuyInsertRequestModel));
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

  @ApiOperation("Item 구매 업데이트 로그")
  @PutMapping(value = "/itembuy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> updateLogItemBuy(
      @RequestBody LogItemBuyUpdateRequestModel itemBuyLogUpdateRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    LogItemBuyUpdateRequestValidator.builder().build()
        .validate(itemBuyLogUpdateRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(logItemBuyService.updateItemBuyLog(itemBuyLogUpdateRequestModel));
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
