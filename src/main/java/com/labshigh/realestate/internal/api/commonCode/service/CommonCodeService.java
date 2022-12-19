package com.labshigh.realestate.internal.api.commonCode.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.labshigh.realestate.core.utils.StringUtils;
import com.labshigh.realestate.internal.api.common.Constants;
import com.labshigh.realestate.internal.api.common.exceptions.ServiceException;
import com.labshigh.realestate.internal.api.commonCode.dao.CommonCodeDAO;
import com.labshigh.realestate.internal.api.commonCode.mapper.CommonCodeMapper;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeInsertRequestModel;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeListRequestModel;
import com.labshigh.realestate.internal.api.commonCode.model.request.CommonCodeUpdateRequestModel;
import com.labshigh.realestate.internal.api.commonCode.model.response.CommonCodeListResponseModel;
import com.labshigh.realestate.internal.api.commonCode.model.response.CommonCodeResponseModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
public class CommonCodeService {

  @Value("${ncloud.access-key}")
  private String ncloudAccessKey;
  @Value("${ncloud.secret-key}")
  private String ncloudSecretKey;
  @Value("${ncloud.object-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.object-storage.region}")
  private String s3Region;
  @Value("${ncloud.object-storage.bucket}")
  private String s3Bucket;

  private final CommonCodeMapper commonCodeMapper;

  public CommonCodeService(CommonCodeMapper commonCodeMapper) {
    this.commonCodeMapper = commonCodeMapper;
  }

  public void insert(CommonCodeInsertRequestModel commonCodeInsertRequestModel) throws IOException {

    int count = commonCodeMapper.checkDuplicate(CommonCodeDAO.builder()
        .name(commonCodeInsertRequestModel.getName())
        .parentCommonCodeUid(commonCodeInsertRequestModel.getParentCommonCodeUid())
        .build());

    if (count >= 1) {
      throw new ServiceException(Constants.MSG_DUPLICATED_DATA);
    }

    CommonCodeDAO commonCodeDAO = CommonCodeDAO.builder()
        .name(commonCodeInsertRequestModel.getName())
        .nameKr(commonCodeInsertRequestModel.getNameKr())
        .sort(commonCodeInsertRequestModel.getSort())
        .usedFlag(commonCodeInsertRequestModel.isUsedFlag())
        .parentCommonCodeUid(commonCodeInsertRequestModel.getParentCommonCodeUid())
        .build();

    commonCodeMapper.insert(commonCodeDAO);

    if (commonCodeInsertRequestModel.getImage() != null) {

      String image = this._uploadPhoto(commonCodeInsertRequestModel.getImage(),
          commonCodeDAO.getUid());

      commonCodeMapper.updatePhoto(CommonCodeDAO.builder()
          .uid(commonCodeDAO.getUid())
          .image(image)
          .build());
    }

  }

  public void update(CommonCodeUpdateRequestModel commonCodeUpdateRequestModel) throws IOException {

    if (commonCodeMapper.check(commonCodeUpdateRequestModel.getCommonCodeUid()) == 0) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    commonCodeMapper.update(CommonCodeDAO.builder()
        .uid(commonCodeUpdateRequestModel.getCommonCodeUid())
        .name(commonCodeUpdateRequestModel.getName())
        .nameKr(commonCodeUpdateRequestModel.getNameKr())
        .sort(commonCodeUpdateRequestModel.getSort())
        .usedFlag(commonCodeUpdateRequestModel.isUsedFlag())
        .build());

    if (commonCodeUpdateRequestModel.getImage() != null) {

      String image = this._uploadPhoto(commonCodeUpdateRequestModel.getImage(),
          commonCodeUpdateRequestModel.getCommonCodeUid());

      commonCodeMapper.updatePhoto(CommonCodeDAO.builder()
          .uid(commonCodeUpdateRequestModel.getCommonCodeUid())
          .image(image)
          .build());

    }
  }

  public void delete(long commonCodeUid) {

    if (commonCodeMapper.check(commonCodeUid) == 0) {
      throw new ServiceException(Constants.MSG_NO_DATA);
    }

    commonCodeMapper.delete(CommonCodeDAO.builder()
        .deletedFlag(true)
        .uid(commonCodeUid)
        .build());
  }

  public CommonCodeListResponseModel list(CommonCodeListRequestModel commonCodeListRequestModel) {

    int totalCount = commonCodeMapper.count(commonCodeListRequestModel);

    if (totalCount <= 0) {
      return CommonCodeListResponseModel.builder()
          .list(Collections.emptyList())
          .totalCount(totalCount)
          .build();
    }

    List<CommonCodeDAO> commonCodeDAOList = commonCodeMapper.list(commonCodeListRequestModel);

    List<CommonCodeResponseModel> commonCodeResponseModelList = commonCodeDAOList.stream()
        .map(commonCodeDAO -> this._getByDao(commonCodeDAO))
        .collect(Collectors.toList());

    return CommonCodeListResponseModel.builder()
        .list(commonCodeResponseModelList)
        .totalCount(totalCount)
        .build();
  }

  public CommonCodeResponseModel detail(long commonCodeUid) {

    return this._getByDao(commonCodeMapper.detail(commonCodeUid));
  }

  private CommonCodeResponseModel _getByDao(CommonCodeDAO commonCodeDAO) {

    if (commonCodeDAO == null) {
      throw new ServiceException(String.format(Constants.MSG_NO_DATA));
    }

    if (!StringUtils.isEmpty(commonCodeDAO.getImage())) {
      commonCodeDAO.setImage("https://" + s3EndPoint + "/" + s3Bucket + commonCodeDAO.getImage());
    }

    return CommonCodeResponseModel.builder()
        .uid(commonCodeDAO.getUid())
        .createdAt(commonCodeDAO.getCreatedAt())
        .updatedAt(commonCodeDAO.getUpdatedAt())
        .deletedFlag(commonCodeDAO.isDeletedFlag())
        .usedFlag(commonCodeDAO.isUsedFlag())
        .name(commonCodeDAO.getName())
        .nameKr(commonCodeDAO.getNameKr())
        .image(commonCodeDAO.getImage())
        .sort(commonCodeDAO.getSort())
        .parentCommonCodeUid(commonCodeDAO.getParentCommonCodeUid())
        .build();
  }


  private String _uploadPhoto(MultipartFile photo, long commonCodeUid) throws IOException {

    // S3 client
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3Region))
        .withCredentials(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(ncloudAccessKey, ncloudSecretKey)))
        .build();

    String bucketName = s3Bucket;

    // create folder
    String folderName = "commonCode/" + commonCodeUid + "/";

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(0L);
    objectMetadata.setContentType("application/x-directory");
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName,
        new ByteArrayInputStream(new byte[0]), objectMetadata);

    try {
      s3.putObject(putObjectRequest);

      log.debug("Folder %s has been created.", folderName);
    } catch (AmazonS3Exception e) {
      e.printStackTrace();

      throw new AmazonS3Exception(e.getErrorMessage());
    } catch (SdkClientException e) {
      e.printStackTrace();

      throw new SdkClientException(e.getMessage());
    }

    // create local file
    String objectName =
        UUID.randomUUID().toString().replace("-", "") + "." + photo.getOriginalFilename()
            .substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
    File tmpFile = new File("/tmp/" + objectName);

    Files.copy(photo.getInputStream(), tmpFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING);

    // s3 upload
    try {
      s3.putObject(bucketName, folderName + objectName, tmpFile);

      log.debug("Object %s has been created.", objectName);
    } catch (AmazonS3Exception e) {
      e.printStackTrace();

      throw new AmazonS3Exception(e.getErrorMessage());
    } catch (SdkClientException e) {
      e.printStackTrace();

      throw new SdkClientException(e.getMessage());
    }

    // 화일 삭제
    tmpFile.delete();

    // s3 업로드된 화일 접근 권한 변경
    try {
      // get the current ACL
      AccessControlList accessControlList = s3.getObjectAcl(bucketName, folderName + objectName);

      // add read permission to anonymous
      accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

      s3.setObjectAcl(bucketName, folderName + objectName, accessControlList);
    } catch (AmazonS3Exception e) {
      e.printStackTrace();

      throw new AmazonS3Exception(e.getErrorMessage());
    } catch (SdkClientException e) {
      e.printStackTrace();

      throw new SdkClientException(e.getMessage());
    }

    return "/" + folderName + objectName;
  }
}