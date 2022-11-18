package com.labshigh.realestate.internal.api.file.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileUploadResponseModel {

  private String fileUri;
}
