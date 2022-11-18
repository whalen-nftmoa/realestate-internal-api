package com.labshigh.realestate.internal.api.marketItem.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBuyInsertRequestModel {

  private long itemUid;
  private long marketItemUid;
  private String price;
  private String nftId;
  private String contractAddress;
  private int index;
}
