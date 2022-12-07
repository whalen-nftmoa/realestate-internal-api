package com.labshigh.realestate.internal.api.marketItem.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellMemberDao {

  private long memberUid;
  private String walletAddress;
}
