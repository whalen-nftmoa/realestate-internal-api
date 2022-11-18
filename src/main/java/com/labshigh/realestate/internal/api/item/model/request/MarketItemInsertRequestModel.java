package com.labshigh.realestate.internal.api.item.model.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
public class MarketItemInsertRequestModel {

  private long itemUid;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startAt;
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate endAt;
  private String transactionHash;
  private String price;
}
