package com.labshigh.realestate.internal.api.common;

public class ApiPathConstants {

  private ApiPathConstants() {
    throw new IllegalStateException("Constants Class");
  }

  // herokuapp.com
  public static final String API_PATH_HEROKUAPP = "https://nftmoa.herokuapp.com";
  public static final String API_PATH_HEROKUAPP_COLLECTIONS = API_PATH_HEROKUAPP + "/api/getcollections";
  public static final String API_PATH_HEROKUAPP_TOKENS = API_PATH_HEROKUAPP + "/api/getalltokens";
  public static final String API_PATH_HEROKUAPP_TOKEN = API_PATH_HEROKUAPP + "/api/getonetoken";
}