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
public class MarketItemDetailListModel {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;
  private long marketItemUid;
  private long itemBuyUid;
  private boolean sellFlag;
  private long itemUid;
  private String imageUri;
  private BigDecimal price;
  private BigDecimal usdPrice;
  private BigDecimal fogPrice;
  private String indexName;
  private String tokenUri;
  private String transactionHash;

}
