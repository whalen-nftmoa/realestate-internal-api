package com.labshigh.realestate.internal.api.member.service;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.member.dao.MemberDao;
import com.labshigh.realestate.internal.api.member.mapper.MemberMapper;
import com.labshigh.realestate.internal.api.member.model.request.MemberGetByWalletAddressRequestModel;
import com.labshigh.realestate.internal.api.member.model.request.MemberInsertMetaMaskRequestModel;
import com.labshigh.realestate.internal.api.member.model.response.MemberResponseModel;
import java.security.SignatureException;
import javax.naming.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class MemberService {

  private final MemberMapper memberMapper;

  public MemberService(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
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

      return MemberResponseModel.builder()
          .uid(memberDaoGet.getUid())
          .createdAt(memberDaoGet.getCreatedAt())
          .updatedAt(memberDaoGet.getUpdatedAt())
          .deletedFlag(memberDaoGet.isDeletedFlag())
          .usedFlag(memberDaoGet.isUsedFlag())
          .walletAddress(memberDaoGet.getWalletAddress())
          .build();
    } else {

      return MemberResponseModel.builder()
          .uid(memberDao.getUid())
          .createdAt(memberDao.getCreatedAt())
          .updatedAt(memberDao.getUpdatedAt())
          .deletedFlag(memberDao.isDeletedFlag())
          .usedFlag(memberDao.isUsedFlag())
          .walletAddress(memberDao.getWalletAddress())
          .build();
    }
  }

  public MemberResponseModel getByUid(long memberUid) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .uid(memberUid)
        .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return MemberResponseModel.builder()
        .uid(memberDao.getUid())
        .createdAt(memberDao.getCreatedAt())
        .updatedAt(memberDao.getUpdatedAt())
        .deletedFlag(memberDao.isDeletedFlag())
        .usedFlag(memberDao.isUsedFlag())
        .walletAddress(memberDao.getWalletAddress())
        .build();
  }

  public MemberResponseModel getByWalletAddress(MemberGetByWalletAddressRequestModel requestModel) {

    MemberDao memberDao = memberMapper.get(MemberDao.builder()
        .walletAddress(requestModel.getWalletAddress())
        .build());

    if (memberDao == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    return MemberResponseModel.builder()
        .uid(memberDao.getUid())
        .createdAt(memberDao.getCreatedAt())
        .updatedAt(memberDao.getUpdatedAt())
        .deletedFlag(memberDao.isDeletedFlag())
        .usedFlag(memberDao.isUsedFlag())
        .walletAddress(memberDao.getWalletAddress())
        .build();
  }
}
