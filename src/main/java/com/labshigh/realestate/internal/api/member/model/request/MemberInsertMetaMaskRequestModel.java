package com.labshigh.realestate.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInsertMetaMaskRequestModel {

  private String publicKey;
  private String signature;
  private String timestamp;
}
