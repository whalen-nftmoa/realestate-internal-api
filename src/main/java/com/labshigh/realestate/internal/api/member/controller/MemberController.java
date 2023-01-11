package com.labshigh.realestate.internal.api.member.controller;

import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.member.model.request.MemberGetByWalletAddressRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.MemberInsertMetaMaskRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import com.labshigh.realestate.internal.api.member.service.MemberService;
import com.labshigh.realestate.internal.api.member.validator.MemberGetByWalletAddressRequestValidator;
import com.labshigh.realestate.internal.api.member.validator.MemberInsertMetaMaskRequestValidator;
import com.labshigh.realestate.internal.api.member.validator.MemberSendVerifyEmailRequestValidator;
import io.swagger.annotations.ApiOperation;
import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping(value = "/api/member")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @ApiOperation(value = "MetaMask Wallet 저장하기")
  @PostMapping(value = "/metamask", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> insertMetaMask(
      @RequestBody MemberInsertMetaMaskRequestModel memberInsertMetaMaskRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    MemberInsertMetaMaskRequestValidator.builder().build().validate(
        memberInsertMetaMaskRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(memberService.insertMetaMask(memberInsertMetaMaskRequestModel));
      } catch (AuthenticationException e) {
        responseModel.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseModel.setMessage(e.getMessage());
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

  @ApiOperation(value = "memberUid 로 member 가져오기")
  @GetMapping(value = "/{memberUid}")
  public ResponseEntity<String> getByiUid(@PathVariable(value = "memberUid") long memberUid) {

    ResponseModel responseModel = new ResponseModel();

    if (memberUid <= 0) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(String.format(Constants.MSG_REQUIRE_FIELD_ERROR, "uid"));
    } else {
      try {
        responseModel.setData(memberService.getByUid(memberUid));
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

  @ApiOperation(value = "walletAddress 로 member 가져오기")
  @GetMapping(value = "/byWallet/{walletAddress}")
  public ResponseEntity<String> getByWalletAddress(
      @PathVariable(value = "walletAddress") String walletAddress,
      MemberGetByWalletAddressRequestModel memberGetByWalletAddressRequestModel,
      BindingResult bindingResult) {

    ResponseModel responseModel = new ResponseModel();

    memberGetByWalletAddressRequestModel.setWalletAddress(walletAddress);

    MemberGetByWalletAddressRequestValidator.builder().build().validate(
        memberGetByWalletAddressRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {
      try {
        responseModel.setData(
            memberService.getByWalletAddress(memberGetByWalletAddressRequestModel));
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

  @ApiOperation(value = "인증메일 발송")
  @PostMapping(value = "/{memberUid}/verifyEmail", produces = {Constants.RESPONSE_CONTENT_TYPE})
  public ResponseEntity<String> sendVerifyEmail(@PathVariable(value = "memberUid") long memberUid,
      @RequestBody MemberSendVerifyEmailRequestModel memberSendVerifyEmailRequestModel,
      BindingResult bindingResult) {
    memberSendVerifyEmailRequestModel.setMemberUid(memberUid);

    ResponseModel responseModel = new ResponseModel();

    MemberSendVerifyEmailRequestValidator.builder().build()
        .validate(memberSendVerifyEmailRequestModel, bindingResult);

    if (bindingResult.hasErrors()) {
      responseModel.setStatus(HttpStatus.PRECONDITION_FAILED.value());
      responseModel.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage());
    } else {

      try {

        responseModel = memberService.sendVerifyEmail(memberSendVerifyEmailRequestModel);
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
