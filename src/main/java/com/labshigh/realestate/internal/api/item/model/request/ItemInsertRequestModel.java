package com.labshigh.realestate.internal.api.item.model.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class ItemInsertRequestModel {

  List<ItemFileInsertRequestModel> itemFiles;
  private long memberUid;
  private long allocationUid;
  private long statusUid;
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
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate approvalAt;
  private String websiteUri;
  private String detail;


}
