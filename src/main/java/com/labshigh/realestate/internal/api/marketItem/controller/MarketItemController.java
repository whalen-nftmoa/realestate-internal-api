package com.labshigh.realestate.internal.api.marketItem.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.service.MarketItemService;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyInsertRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyListRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemDetailRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemListRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/marketItem")
public class MarketItemController {

  @Autowired
  private MarketItemService marketItemService;

  @ApiOperation("MarketItem 구매")
  @PostMapping(value = "/{marketItemUid}/buy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertItemBuy(
      @RequestBody ItemBuyInsertRequestModel itemBuyInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuyInsertRequestValidator.builder().build()
        .validate(itemBuyInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.insertItemBuy(itemBuyInsertRequestModel));
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

  @ApiOperation("Item 리스트 조회")
  @GetMapping(value = "", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> list(MarketItemListRequestModel marketItemListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemListRequestValidator.builder().build()
        .validate(marketItemListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listMarketItem(marketItemListRequestModel));
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

  @ApiOperation("마켓 Item 디테일 조회")
  @GetMapping(value = "/{marketItemUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> detail(
      @PathVariable(value = "marketItemUid") long marketItemUid,
      MarketItemDetailRequestModel marketItemDetailRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();
    marketItemDetailRequestModel.setMarketItemUid(marketItemUid);

    MarketItemDetailRequestValidator.builder().build()
        .validate(marketItemDetailRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.detailMarketItem(marketItemDetailRequestModel));
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

  @ApiOperation("Item Buy 리스트 조회")
  @GetMapping(value = "/{marketItemUid}/buy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> itemBuyList(ItemBuyListRequestModel itemBuyListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    ItemBuyListRequestValidator.builder().build()
        .validate(itemBuyListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listItemBuy(itemBuyListRequestModel));
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
