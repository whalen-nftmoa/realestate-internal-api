package com.labshigh.realestate.internal.api.commonCode.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CommonCodeUpdateRequestModel {

  private long commonCodeUid;
  private String name;
  private String nameKr;
  private MultipartFile image;
  private int sort;
  private boolean usedFlag;
}