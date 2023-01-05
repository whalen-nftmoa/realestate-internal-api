package com.labshigh.realestate.internal.api.marketItem.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketItemDetailListRequestModel {

  @ApiModelProperty(value = "marketItemUid (단일 구매)")
  private long marketItemUid;
  @ApiModelProperty(value = "marketItemDetailUid 리스트 (여러개 구매)")
  private List<Long> marketItemDetailUidList;
  @ApiModelProperty(value = "정렬")
  private SortType sort;

  public enum SortType {
    lowPrice,
    highPrice
  }
}

