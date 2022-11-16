package com.labshigh.realestate.internal.api.commonCode.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CommonCodeListResponseModel {

  private int totalCount;
  private List<CommonCodeResponseModel> list;
}