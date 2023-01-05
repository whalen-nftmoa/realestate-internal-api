package com.labshigh.realestate.internal.api.marketItem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketItemHistoryListResponseModel {

  private String event;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  private String transactionHash;
  private BigDecimal price;
  private String number;
  private String fromWalletAddress;
  private String toWalletAddress;
  private BigDecimal usdPrice;
  private BigDecimal fogPrice;
  private BigDecimal totalPrice;
  private BigDecimal usdTotalPrice;
  private BigDecimal fogTotalPrice;
  private long quantity;

}
