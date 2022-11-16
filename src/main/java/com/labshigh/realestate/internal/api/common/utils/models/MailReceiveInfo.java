package com.labshigh.realestate.internal.api.common.utils.models;

import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MailReceiveInfo {
    private String address;
    private String name;
    private String type;    // R : 수신자, C : 참조자, B : 숨은참조
    private String sendType;
    private HashMap<String,String> parameters;

}
