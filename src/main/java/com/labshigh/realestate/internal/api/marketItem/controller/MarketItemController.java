package com.labshigh.realestate.internal.api.marketItem.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListByUidRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemBuyListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.ItemRebuyInsertRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemCancelResellRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDeleteRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemDetailRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.model.request.MarketItemResellListRequestModel;
import com.labshigh.realestate.internal.api.marketItem.service.MarketItemService;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyInsertRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyListByMemberRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyListByUidRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemBuyListRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.ItemRebuyInsertRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemCancelResellRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemDeleteRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemDetailListRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemDetailRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemListRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemMyResellListRequestValidator;
import com.labshigh.realestate.internal.api.marketItem.validator.MarketItemResellListRequestValidator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/marketItem")
public class MarketItemController {

  @Autowired
  private MarketItemService marketItemService;

  @ApiOperation("MarketItem ??????")
  @PostMapping(value = "/{marketItemUid}/buy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertItemBuy(
      @RequestBody ItemBuyInsertRequestModel itemBuyInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    itemBuyInsertRequestModel.setItemKind(2);

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

  @ApiOperation("MarketItem ?????????")
  @PostMapping(value = "/rebuy", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertItemRebuy(
      @RequestBody ItemRebuyInsertRequestModel itemRebuyInsertRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    itemRebuyInsertRequestModel.setItemKind(2);

    ItemRebuyInsertRequestValidator.builder().build()
        .validate(itemRebuyInsertRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.insertItemRebuy(itemRebuyInsertRequestModel);
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

  @ApiOperation("Item ????????? ??????")
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


  @ApiOperation("????????? ????????? ????????? ??????")
  @GetMapping(value = "/{marketItemUid}/resell", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listMarketItemResell(
      @PathVariable(value = "marketItemUid") long marketItemUid,
      MarketItemResellListRequestModel marketItemResellListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();
    marketItemResellListRequestModel.setMarketItemUid(marketItemUid);

    MarketItemResellListRequestValidator.builder().build()
        .validate(marketItemResellListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            marketItemService.listMarketItemResell(marketItemResellListRequestModel));
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

  @ApiOperation("????????? ????????? ????????? ????????? ??????")
  @GetMapping(value = "/{marketItemUid}/myResell", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listMarketItemMyResell(
      @PathVariable(value = "marketItemUid") long marketItemUid,
      MarketItemResellListRequestModel marketItemResellListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();
    marketItemResellListRequestModel.setMarketItemUid(marketItemUid);

    MarketItemMyResellListRequestValidator.builder().build()
        .validate(marketItemResellListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            marketItemService.listMarketItemMyResell(marketItemResellListRequestModel));
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

  @ApiOperation("?????? ?????????????????? ?????? ????????? ????????? ??????")
  @GetMapping(value = "/marketItemDetail", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> listMarketItemDetail(
      MarketItemDetailListRequestModel marketItemDetailListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    MarketItemDetailListRequestValidator.builder().build()
        .validate(marketItemDetailListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            marketItemService.listMarketItemDetail(marketItemDetailListRequestModel));
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

  @ApiOperation("?????? Item ????????? ??????")
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

  @ApiOperation("Item Buy ????????? ??????")
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

  @ApiOperation("Item Buy ????????? ????????? ?????? ??????")
  @GetMapping(value = "/{marketItemUid}/buy/all", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> itemBuyAllList(
      ItemBuyListByUidRequestModel itemBuyListByUidRequestModel,
      @PathVariable(value = "marketItemUid") long marketItemUid,
      BindingResult bindingResult) {

    itemBuyListByUidRequestModel.setMarketItemUid(marketItemUid);

    ResponseModel responseModel = new ResponseModel();

    ItemBuyListByUidRequestValidator.builder().build()
        .validate(itemBuyListByUidRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listItemByMember(itemBuyListByUidRequestModel));
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


  @ApiOperation("Item Buy ?????????(?????? ??????) ??????")
  @GetMapping(value = "buy/member/{memberUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> itemBuyListByMember(
      @PathVariable(value = "memberUid") long memberUid,
      ItemBuyListRequestModel itemBuyListRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    itemBuyListRequestModel.setMemberUid(memberUid);

    ItemBuyListByMemberRequestValidator.builder().build()
        .validate(itemBuyListRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(marketItemService.listItemBuyByMember(itemBuyListRequestModel));
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

  //marketItemCancelResell
  @ApiOperation("????????? ????????? ????????? ?????? ??????")
  @PutMapping(value = "/{marketItemUid}/cancelResell", produces = {
      Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> marketItemCancelResell(
      @PathVariable(value = "marketItemUid") long marketItemUid,
      @RequestBody MarketItemCancelResellRequestModel marketItemCancelResellRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();
    marketItemCancelResellRequestModel.setMarketItemUid(marketItemUid);

    MarketItemCancelResellRequestValidator.builder().build()
        .validate(marketItemCancelResellRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.marketItemCancelResell(marketItemCancelResellRequestModel);
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

  @ApiOperation("MarketItem ??????")
  @DeleteMapping(value = "/{marketItemUid}", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> deleteMarketItem(
      @PathVariable(value = "marketItemUid") long marketItemUid,
      @RequestBody MarketItemDeleteRequestModel marketItemDeleteRequestModel,
      BindingResult bindingResult) {
    ResponseModel responseModel = new ResponseModel();

    marketItemDeleteRequestModel.setMarketItemUid(marketItemUid);

    MarketItemDeleteRequestValidator.builder().build()
        .validate(marketItemDeleteRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        marketItemService.deleteMarketItem(marketItemDeleteRequestModel);
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
