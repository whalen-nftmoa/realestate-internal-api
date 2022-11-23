package com.labshigh.realestate.internal.api.marketItem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyListResponseModel {

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
  private String price;
  private String usdPrice;
  private String usdtPrice;
  private String contractAddress;
  private String nftId;
  private long memberUid;
  private long allocationUid;
  private long statusUid;
  private String allocationName;
  private String statusName;
  private String imageUri;
  private String projectName;
  private String totalPrice;
  private String usdTotalPrice;
  private String usdtTotalPrice;
  private int allocationDay;
  private String right;
  private String location;
  private String coreValue;
  private String area;
  private String scale;
  private String purpose;
  private String companyName;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime approvalAt;
  private String websiteUri;
  private String detail;
  private String tokenUri;
  private String walletAddress;

}
