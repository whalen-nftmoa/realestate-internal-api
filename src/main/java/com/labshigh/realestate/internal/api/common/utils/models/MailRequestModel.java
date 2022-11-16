package com.labshigh.realestate.internal.api.common.utils.models;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestModel {
    private String senderAddress;
    private String senderName;
    private Integer templateSid;
    private String title;
    private String body;
    private Boolean individual; //
    private Boolean confirmAdnSend; // 확인 후 발송
    //private HashMap<String,String> parameters;
    private List<MailReceiveInfo> recipients;
    private String unsubscribeMessage; // 사용자 정의 수신 거부 문구
}
