package com.labshigh.realestate.internal.api.item.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemFileMetaDataModel {

  private String categoryName;
  private String fileUri;
}
