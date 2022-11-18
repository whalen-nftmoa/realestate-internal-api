package com.labshigh.realestate.internal.api.exchange.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetRequestModel;
import com.labshigh.realestate.internal.api.exchange.model.request.ExchangeGetVirtualRequestModel;
import com.labshigh.realestate.internal.api.exchange.service.ExchangeService;
import com.labshigh.realestate.internal.api.exchange.validator.ExchangeGetRequestValidator;
import com.labshigh.realestate.internal.api.exchange.validator.ExchangeGetVirtualRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/exchange")
public class ExchangeController {

  private final ExchangeService exchangeService;

  public ExchangeController(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @ApiOperation(value = "가상 화폐 환율")
  @GetMapping(value = "/virtual/{exchangeName}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getVirtual(
      @PathVariable(value = "exchangeName") String exchangeName,
      ExchangeGetVirtualRequestModel exchangeGetVirtualRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    exchangeGetVirtualRequestModel.setExchangeName(exchangeName);

    ExchangeGetVirtualRequestValidator.builder().build().validate(
        exchangeGetVirtualRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(exchangeService.getVirtual(exchangeGetVirtualRequestModel));
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

  @ApiOperation(value = "화폐 환율")
  @GetMapping(value = "/{exchangeName}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> getVirtual(
      @PathVariable(value = "exchangeName") String exchangeName,
      ExchangeGetRequestModel exchangeGetRequestModel, BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    exchangeGetRequestModel.setExchangeName(exchangeName);

    ExchangeGetRequestValidator.builder().build().validate(
        exchangeGetRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(exchangeService.get(exchangeGetRequestModel));
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
