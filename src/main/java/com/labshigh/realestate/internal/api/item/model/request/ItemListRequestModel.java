package com.labshigh.realestate.internal.api.item.model.request;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.utils.PageUtils;
import com.labshigh.realestate.internal.api.common.utils.PageUtils.OffsetAndRowCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemListRequestModel {

  @ApiModelProperty(value = "페이지")
  private int page = 1;

  @ApiModelProperty(value = "페이지당 row 개수")
  private int size = Constants.DEFAULT_PAGE_SIZE;

  private OffsetAndRowCount offsetAndRowCount = PageUtils.convertPageToOffset(page, size);

  public void setPage(int page) {
    this.page = page;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }

  public void setSize(int size) {
    this.size = size;
    this.offsetAndRowCount = PageUtils.convertPageToOffset(page, size);
  }
}
