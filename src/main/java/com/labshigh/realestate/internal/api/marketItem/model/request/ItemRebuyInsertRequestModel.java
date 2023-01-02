package com.labshigh.realestate.internal.api.marketItem.model.request;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRebuyInsertRequestModel {

  private long memberUid;
  private long itemUid;
  private long marketItemUid;
  private BigDecimal price;
  private String nftId;
  private String contractAddress;
  private int quantity;
  private int currentQuantity;
  private int index;
  private int itemKind;
  private TransactionHash transactionHash;
  private List<Long> marketItemDetailUidList;


}
