package com.labshigh.realestate.internal.api.member.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VerifyEmailModel {

  private long uid;
  private long verifyTime;
  private String email;
  private Integer templateId;
}
