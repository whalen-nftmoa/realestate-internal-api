package com.labshigh.realestate.internal.api.marketItem.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketItemCancelResellRequestModel {

  @ApiModelProperty(value = "marketItemUid")
  private long marketItemUid;
  @ApiModelProperty(value = "memberUid")
  private long memberUid;
  @ApiModelProperty(value = "취소 트랜잭션 해쉬")
  private TransactionHash cancelTransactionHash;

}

