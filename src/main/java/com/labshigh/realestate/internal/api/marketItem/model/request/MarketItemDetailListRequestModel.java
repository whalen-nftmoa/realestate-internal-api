package com.labshigh.realestate.internal.api.marketItem.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketItemDetailListRequestModel {

  @ApiModelProperty(value = "marketItemUid")
  private long marketItemUid;
  @ApiModelProperty(value = "정렬")
  private SortType sort;

  public enum SortType {
    lowPrice,
    highPrice
  }
}

