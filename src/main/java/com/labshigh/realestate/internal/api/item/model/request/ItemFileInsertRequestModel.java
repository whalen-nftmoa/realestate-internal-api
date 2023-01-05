package com.labshigh.realestate.internal.api.item.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFileInsertRequestModel {

  private Long categoryUid;
  private String categoryName;
  private Long itemUid;
  private String fileUri;
}
