package com.labshigh.realestate.internal.api.marketItem.model.request;

import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.utils.PageUtils;
import com.labshigh.realestate.internal.api.common.utils.PageUtils.OffsetAndRowCount;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ItemBuyListRequestModel {

  @ApiModelProperty(value = "MarketItemUid")
  private long marketItemUid;
  @ApiModelProperty(value = "memberUid")
  private long memberUid;

  @ApiModelProperty(value = "지갑주소 검색 주소")
  public String searchValue;

  @ApiModelProperty(value = "시작 날짜")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startDate;

  @ApiModelProperty(value = "종료 날짜")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endDate;

  @ApiModelProperty(value = "페이지")
  private int page = 1;

  @ApiModelProperty(value = "페이지당 row 개수")
  private int size = Constants.MAX_LIST_PAGE_SIZE;

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
