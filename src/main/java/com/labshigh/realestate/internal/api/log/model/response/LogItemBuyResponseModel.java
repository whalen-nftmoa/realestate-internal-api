package com.labshigh.realestate.internal.api.log.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LogItemBuyResponseModel {

  private long uid;
  private long marketItemUid;
  private long memberUid;
  private long itemBuyUid;
  private String status;
  private String code;
  private String message;
  private String data;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;

}
