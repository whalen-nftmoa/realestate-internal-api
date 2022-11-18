package com.labshigh.realestate.internal.api.exchange.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExchangeVirtualResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private boolean usedFlag;

  private String name;
  private String last;
  private String lowestAsk;
  private String highestBid;
  private String percentChange;
  private String baseVolume;
  private String quoteVolume;
  private String isFrozen;
  private String postOnly;
  private String high24hr;
  private String low24hr;
}
