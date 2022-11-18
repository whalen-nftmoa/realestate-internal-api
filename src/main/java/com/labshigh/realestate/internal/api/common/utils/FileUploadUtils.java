package com.labshigh.realestate.internal.api.common.utils;

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
import com.labshigh.realestate.core.utils.JsonUtils;
import com.labshigh.realestate.internal.api.common.utils.enums.FileType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Component
public class FileUploadUtils {

  @Value("${ncloud.access-key}")
  private String ncloudAccessKey;
  @Value("${ncloud.secret-key}")
  private String ncloudSecretKey;
  @Value("${ncloud.nft-storage.end-point}")
  private String s3EndPoint;
  @Value("${ncloud.nft-storage.region}")
  private String s3Region;
  @Value("${ncloud.nft-storage.bucket}")
  private String s3NftBucket;

  public String uploadPhoto(MultipartFile file, FileType fileType, Long commonCodeUid)
      throws IOException {

    String pathName = UUID.randomUUID().toString().replace("-", "");
    String folderName = this.getUploadPath(fileType, pathName, commonCodeUid);
    String bucketName = s3NftBucket;

    // create local file
    String objectName = pathName + "." + file.getOriginalFilename()
        .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

    File tmpFile = new File("/tmp/" + objectName);

    Files.copy(file.getInputStream(), tmpFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING);

    this.upload(tmpFile, objectName, bucketName, folderName);
    return "https://" + s3EndPoint + "/" + s3NftBucket + "/" + folderName + objectName;
  }

  public String uploadByObject(Object obj, String pathName, FileType fileType) throws IOException {
    String folderName = this.getUploadPath(fileType, pathName, null);
    String buketName = s3NftBucket;
    String metadataJson = JsonUtils.convertObjectToJsonString(obj, true);
    String objectName = "metadata.json";
    String tempName = UUID.randomUUID().toString().replace("-", "") + ".json";
    File tmpFile = new File("/tmp/" + tempName);
    InputStream inputStream = new ByteArrayInputStream(metadataJson.getBytes());
    Files.copy(inputStream, tmpFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING);
    this.upload(tmpFile, objectName, buketName, folderName);
    return "https://" + s3EndPoint + "/" + s3NftBucket + "/" + folderName + objectName;
  }

  private void upload(File tmpFile, String objectName, String bucketName, String folderName)
      throws IOException {
    // S3 client
    final AmazonS3 s3 = getAmazonS3();

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
  }

  private String getUploadPath(FileType fileType, String pathName, Long commonCodeUid) {
    String result = null;

    switch (fileType) {
      case nft:
        result = "nft/" + pathName + "/";
        break;
      case nftAttach:
        result = commonCodeUid.toString() + "/" + pathName + "/";
        break;
    }
    return result;
  }

  private AmazonS3 getAmazonS3() {
    // S3 client
    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3EndPoint, s3Region))
        .withCredentials(new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(ncloudAccessKey, ncloudSecretKey)))
        .build();

    return s3;
  }

}
