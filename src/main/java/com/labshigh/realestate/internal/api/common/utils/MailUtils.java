package com.labshigh.realestate.internal.api.common.utils;


import com.labshigh.realestate.core.helper.WebClientHelper;
import com.labshigh.realestate.core.models.ResponseModel;
import com.labshigh.realestate.core.utils.JsonUtils;
import com.labshigh.realestate.internal.api.common.utils.models.MailRequestModel;
import java.net.URI;
import java.util.Base64;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MailUtils {

  @Value("${ncloud.access-key}")
  private String accessKey;
  @Value("${ncloud.secret-key}")
  private String secretKey;
  @Value("${ncloud.mail-storage.end-point}")
  private String mailEndPoint;

  public ResponseModel send(MailRequestModel model) {

    String timestamp = String.valueOf(new Date().getTime());
    ResponseModel result = new ResponseModel();
    try {
      String signatureKey = getSignature(timestamp);

      HttpHeaders headers = new HttpHeaders();
      headers.add("x-ncp-apigw-timestamp", timestamp);
      headers.add("x-ncp-iam-access-key", accessKey);
      headers.add("x-ncp-apigw-signature-v2", signatureKey);

      URI uri = new URI(mailEndPoint);
      log.debug("model : {}", JsonUtils.convertObjectToJsonString(model, true));
      String sendResult = WebClientHelper.getInstance().post(uri, model, String.class, headers);
      log.debug("result : {}", result);
      result.setData(sendResult);
    } catch (Exception ex) {
      result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      result.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.error.setErrorMessage(ex.getLocalizedMessage());
    }
    return result;
  }

  private String getSignature(String timestamp) throws Exception {
    String space = " ";  // 공백
    String newLine = "\n";  // 줄바꿈
    String method = "POST";  // HTTP 메소드
    String url = "/api/v1/mails";  // 도메인을 제외한 "/" 아래 전체 url (쿼리스트링 포함)
//        String timestamp = String.valueOf(new Date().getTime());  // 현재 타임스탬프 (epoch, millisecond)

    String message = new StringBuilder()
        .append(method)
        .append(space)
        .append(url)
        .append(newLine)
        .append(timestamp)
        .append(newLine)
        .append(accessKey)
        .toString();

    SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(signingKey);

    byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
    String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

    return encodeBase64String;
  }

}

