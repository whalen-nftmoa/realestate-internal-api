package com.labshigh.realestate.internal.api.common.utils.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class SmsMessage {
  private String to;
  private String subject;
  private String content;
}
