package com.labshigh.realestate.internal.api.item.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class MarketItemInsertRequestModel {

  private long itemUid;
  private long memberUid;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startAt;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate endAt;
  private String transactionHash;
  private BigDecimal price;
  private BigDecimal fogPrice;


  /*
   * 재판매시 사용되는 파라미터들
   * */
  private long marketItemUid;
  private List<Long> itemBuyUidList;

}
