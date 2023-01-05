package com.labshigh.realestate.internal.api.item.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labshigh.realestate.internal.api.common.Constants;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemMetaDataModel {

  //메타데이터.json 저장 용
  List<ItemFileMetaDataModel> itemFiles;

  private String allocationName;
  private String statusName;
  private String imageUri;
  private String projectName;
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
