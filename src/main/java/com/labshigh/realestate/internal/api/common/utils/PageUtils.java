package com.labshigh.realestate.internal.api.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PageUtils {

  private PageUtils() {
    throw new IllegalStateException("Utils Class");
  }

  @Builder
  @Getter @Setter
  public static class OffsetAndRowCount {
    @ApiModelProperty(hidden = true)
    int offset;
    @ApiModelProperty(hidden = true)
    int rowCount;
  }

  public static OffsetAndRowCount convertPageToOffset(int page, int size) {

    return OffsetAndRowCount.builder()
        .offset(size * (page-1))
        .rowCount(size)
        .build();
  }
}