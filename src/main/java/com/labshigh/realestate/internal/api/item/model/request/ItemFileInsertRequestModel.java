package com.labshigh.realestate.internal.api.item.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFileInsertRequestModel {

  private long categoryUid;
  private String categoryName;
  private long itemUid;
  private String fileUri;
}
