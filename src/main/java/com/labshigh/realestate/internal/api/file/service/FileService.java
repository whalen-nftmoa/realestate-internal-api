package com.labshigh.realestate.internal.api.file.service;

import com.labshigh.realestate.internal.api.common.utils.FileUploadUtils;
import com.labshigh.realestate.internal.api.file.model.request.FileUploadRequestModel;
import com.labshigh.realestate.internal.api.file.model.response.FileUploadResponseModel;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FileService {

  @Autowired
  FileUploadUtils fileUploadUtils;

  public FileUploadResponseModel upload(FileUploadRequestModel requestModel) throws IOException {
    return FileUploadResponseModel.builder()
        .fileUri(fileUploadUtils.uploadPhoto(requestModel.getFile(),
            requestModel.getFileType(), requestModel.getCommonCodeUid()))
        .build();
  }
}
