package com.labshigh.realestate.internal.api.file.model.request;

import com.labshigh.realestate.internal.api.common.utils.enums.FileType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadRequestModel {

  private long memberUid;
  private FileType fileType;
  private MultipartFile file;
  private Long commonCodeUid;
}
