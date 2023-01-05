package com.labshigh.realestate.internal.api.marketItem.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemHistoryListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.service.MarketItemService;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemHistoryListRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/marketItem/history")
public class MarketItemHistoryController {

  @Autowired
  private MarketItemService marketItemService;

  @ApiOperation("member 리스트 조회")
  @GetMapping(value = "/member", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listSellMember() {
    ResponseModel responseModel = new ResponseModel();

    try {
      responseModel.setData(marketItemService.listSellMember());
    } catch (ServiceException e) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(e.getMessage());
    } catch (Exception e) {
      responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      responseModel.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      responseModel.error.setErrorMessage(e.getLocalizedMessage());
    }

    return responseModel.toResponse();
  }

  @ApiOperation("MarketItem 히스토리 리스트 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listMarketItemHistory(
      MarketItemHistoryListRequestModel marketItemHistoryListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemHistoryListRequestValidator.builder().build()
        .validate(marketItemHistoryListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {

      try {
        responseModel.setData(
            marketItemService.listMarketItemHistory(marketItemHistoryListRequestModel));
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
