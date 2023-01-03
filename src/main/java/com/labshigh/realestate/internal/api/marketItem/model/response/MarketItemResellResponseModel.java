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
public class MarketItemResellResponseModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;
  private long quantity;
  private long currentQuantity;

  private BigDecimal price;
  private BigDecimal usdPrice;
  private BigDecimal fogPrice;
  private String transactionHash;
  private String sellId;
  private String nftId;
  private int mintingStatus;
  private BigDecimal totalPrice;
  private BigDecimal usdTotalPrice;
  private BigDecimal fogTotalPrice;
  private String indexName;
  private String walletAddress;
  private String projectName;
  private String imageUri;
  private String tokenUri;
  private long itemUid;
}
