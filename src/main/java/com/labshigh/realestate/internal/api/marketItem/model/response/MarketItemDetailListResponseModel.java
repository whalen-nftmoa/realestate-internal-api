package com.labshigh.realestate.internal.api.marketItem.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MarketItemDetailListResponseModel {

  private List<MarketItemDetailListModel> detailList;
  private List<ItemFileResponseModel> fileList;
}
