package com.labshigh.realestate.internal.api.member.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSendVerifyEmailRequestModel {

  private long memberUid;
  private String email;
}
