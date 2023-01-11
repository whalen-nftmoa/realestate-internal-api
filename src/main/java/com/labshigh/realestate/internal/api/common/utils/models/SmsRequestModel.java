package com.labshigh.realestate.internal.api.common.utils.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestModel {

  private String type;
  private String contentType;
  private String countryCode;
  private String from;
  private String subject;
  private String content;
  private List<SmsMessage> messages;
  private List<SmsFile> smsFiles;
  private String reserveTime;
  private String receiveTimeZone;

}
