package com.labshigh.realestate.internal.api.member.service;

import com.labshigh.realestate.core.helper.CryptoHelper;
import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.core.utils.JsonUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.common.utils.MailUtils;
import com.labshigh.realestate.internal.api.common.utils.SmsUtils;
import com.labshigh.realestate.internal.api.common.utils.models.MailReceiveInfo;
import com.labshigh.realestate.internal.api.common.utils.models.MailRequestModel;
import com.labshigh.realestate.internal.api.member.dao.MemberDao;
import com.labshigh.realestate.internal.api.member.mapper.MemberMapper;
import com.labshigh.realestate.internal.api.member.model.request.MemberGetByWalletAddressRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.MemberInsertMetaMaskRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.MemberSendVerifyEmailRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.VerifyEmailModel;
import com.labshigh.realestate.internal.api.member.model.response.MemberResponseModel;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MemberService {

  private final MemberMapper memberMapper;
  private final MailUtils mailUtils;
  private final SmsUtils smsUtils;

  @Value("${ncloud.mail-storage.email-verify-template-id}")
  private Integer emailVerifiedTemplateId;
  @Value("${ncloud.mail-storage.tokenExpirationTime}")
  private long tokenExpirationTime;
  @Value("${ncloud.sms-storage.from-phone-number}")
  private String fromPhoneNumber;
  @Value("${ncloud.sms-storage.expirationTime}")
  private long smsExpirationTime;

  public MemberService(MemberMapper memberMapper, MailUtils mailUtils, SmsUtils smsUtils) {
    this.memberMapper = memberMapper;
    this.mailUtils = mailUtils;
    this.smsUtils = smsUtils;
  }

  @Transactional
  public MemberResponseModel insertMetaMask(MemberInsertMetaMaskRequestModel requestModel)
      throws SignatureException, AuthenticationException {

    // Wallet Address 가져오기
    String walletAddress = requestModel.getPublicKey().trim();

    log.debug("Wallet Address : {}", walletAddress);

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .walletAddress(walletAddress)
        .build());

    // 해당 wallet address 로 가입이 된 내역이 없을 경우
    if (memberDao == null) {

      // member main table insert
      MemberDao memberDaoInsert = MemberDao.builder().walletAddress(walletAddress).build();

      memberMapper.insert(memberDaoInsert);

      MemberDao memberDaoGet = memberMapper.get(MemberDao.builder()
          .uid(memberDaoInsert.getUid())
          .build());

      return convertMemberResponseModel(memberDaoGet);
    } else {

      return convertMemberResponseModel(memberDao);
    }
  }

  public MemberResponseModel getByUid(long memberUid) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .uid(memberUid)
        .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return convertMemberResponseModel(memberDao);
  }

  public MemberResponseModel getByWalletAddress(MemberGetByWalletAddressRequestModel requestModel) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .walletAddress(requestModel.getWalletAddress())
        .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return convertMemberResponseModel(memberDao);
  }

  @Transactional
  public ResponseModel sendVerifyEmail(MemberSendVerifyEmailRequestModel requestModel) {

    MemberDao dao = memberMapper.get(MemberDao.builder()
        .uid(requestModel.getMemberUid())
        .build());

    if (dao == null) {
      throw new ServiceException(Constants.MSG_MEMBER_NO);
    }

    if (dao.getEmail() != null && dao.getEmail().equals(requestModel.getEmail())
        && dao.isEmailVerifiedFlag()) {
      throw new ServiceException(Constants.MSG_ALREADY_VERIFIED_EMAIL);
    }

    HashMap<String, String> customMap = new HashMap<>();

    dao.setEmail(requestModel.getEmail());

    memberMapper.updateEmail(dao);

    return sendVerifyEmail(dao, emailVerifiedTemplateId, customMap);

  }

  private ResponseModel sendVerifyEmail(MemberDao model, Integer templateId,
      HashMap<String, String> customMap) {

    //토큰 생성
    VerifyEmailModel tokenModel = VerifyEmailModel.builder()
        .uid(model.getUid())
        .email(model.getEmail())
        .verifyTime(
            Timestamp.valueOf(LocalDateTime.now().plusMinutes(tokenExpirationTime)).getTime())
        .templateId(templateId)
        .build();

    String tokenJson = CryptoHelper.encrypt(JsonUtils.convertObjectToJsonString(tokenModel));
    customMap.put("token", tokenJson);

    MailReceiveInfo mailReceiveInfo = MailReceiveInfo.builder()
        .address(model.getEmail())
        //.name(model.getName())
        .type("R")
        .parameters(customMap)
        .build();

    List<MailReceiveInfo> receiveInfoList = new ArrayList<>();
    receiveInfoList.add(mailReceiveInfo);
    MailRequestModel mailRequestModel = MailRequestModel.builder()
        .templateSid(templateId)
        .individual(true)
        .recipients(receiveInfoList)
        .build();

    ResponseModel result = mailUtils.send(mailRequestModel);
    log.debug("mailSendResult : {}", JsonUtils.convertObjectToJsonString(result));

    return result;
  }

  private MemberResponseModel convertMemberResponseModel(MemberDao dao) {
    return MemberResponseModel.builder()
        .uid(dao.getUid())
        .createdAt(dao.getCreatedAt())
        .updatedAt(dao.getUpdatedAt())
        .deletedFlag(dao.isDeletedFlag())
        .usedFlag(dao.isUsedFlag())
        .walletAddress(dao.getWalletAddress())
        .email(dao.getEmail())
        .emailVerifiedFlag(dao.isEmailVerifiedFlag())
        .phoneNumber(dao.getPhoneNumber())
        .phoneVerifiedFlag(dao.isPhoneVerifiedFlag())
        .emailNewsletterFlag(dao.isEmailNewsletterFlag())
        .build();
  }


}
