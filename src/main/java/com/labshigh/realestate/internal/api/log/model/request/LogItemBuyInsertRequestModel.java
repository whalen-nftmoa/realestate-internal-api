package com.labshigh.realestate.internal.api.log.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogItemBuyInsertRequestModel {

  private long uid;
  private long marketItemUid;
  private long memberUid;
  private String status;
  private String code;
  private String message;
  private String data;

}
