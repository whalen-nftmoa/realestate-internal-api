package com.labshigh.realestate.internal.api.marketItem.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemBuyDetailDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long itemUid;
  private long marketItemUid;
  private int quantity;
  private int currentQuantity;
  private BigDecimal price;
  private BigDecimal usdPrice;
  private BigDecimal fogPrice;
  private String contractAddress;
  private String nftId;
  private long memberUid;
  private long allocationUid;
  private long statusUid;
  private String allocationName;
  private String statusName;
  private String imageUri;
  private String projectName;
  private BigDecimal totalPrice;
  private BigDecimal usdTotalPrice;
  private BigDecimal fogTotalPrice;
  private int allocationDay;
  private String right;
  private String location;
  private String coreValue;
  private String area;
  private String scale;
  private String purpose;
  private String companyName;
  private String indexName;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime approvalAt;
  private String websiteUri;
  private String detail;
  private String tokenUri;
  private String walletAddress;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime startAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime endAt;

  private String transactionHash;


}
