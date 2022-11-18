package com.labshigh.realestate.internal.api.item.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
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
public class ItemDao {

  private long uid;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime createdAt;
  @JsonFormat(pattern = Constants.JSONFY_DATE_FORMAT)
  private LocalDateTime updatedAt;
  private boolean deletedFlag;
  private Boolean usedFlag;

  private long memberUid;
  private long allocationUid;
  private long statusUid;
  private String allocationName;
  private String statusName;
  private String imageUri;
  private String projectName;
  private String totalPrice;
  private int quantity;
  private int currentQuantity;
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


}
