package com.labshigh.realestate.internal.api.marketItem.model.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemBuyListByUidRequestModel {

  @ApiModelProperty(value = "marketItemUid")
  private long marketItemUid;
  @ApiModelProperty(value = "itemBuyUidList")
  private List<Long> itemBuyUidList;
}
