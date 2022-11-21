package com.labshigh.realestate.internal.api.common;

public class Constants {

  // 응답 컨텐츠 타입
  public static final String REQUEST_CONTENT_TYPE = "application/json; charset=UTF-8";
  public static final String RESPONSE_CONTENT_TYPE = "application/json; charset=UTF-8";

  // iso 8601 json 날짜 포멧
  public static final String JSONFY_LOCAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

  public static final String JSONFY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  public static final String ES_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.Z";
  public static final String VIEW_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String UI_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String TIMEZONE = "Asia/Seoul";

  // 기본 페이지 사이즈
  public static final int DEFAULT_PAGE_SIZE = 20;
  // 최대 페이지 사이즈
  public static final int MAX_LIST_PAGE_SIZE = 250;

  // http status
  public static final String RESPONSE_CODE_CORE_ERROR_MSG = "CORE API Response Error";

  // 이메일 최소/최대 글자수
  public static final int MIN_EMAIL_INPUT_SIZE = 5;
  public static final int MAX_EMAIL_INPUT_SIZE = 256;

  // 비밀번호 최소 글자수
  public static final int MIN_PASSWORD_INPUT_SIZE = 6;

  public static final String MSG_NO_DATA = "데이터가 없습니다..";
  public static final String MSG_DUPLICATED_DATA = "이미 중복된 데이터가 존재합니다.";
  public static final String MSG_REQUIRE_FIELD_ERROR = "%s 필드의 값을 입력해 주세요.";

  public static final String MSG_MIN_LENGTH_FIELD_ERROR = "최소 %d자 이상 입력해야합니다.";
  public static final String MSG_MIN_LENGTH_OVER_ERROR = "최소 %d개 이상 입력해야합니다.";
  public static final String MSG_MAX_LENGTH_FIELD_ERROR = "최대 %d자까지 입력 가능합니다.";
  public static final String MSG_MAX_LENGTH_OVER_ERROR = "최대 %d개까지 입력 가능합니다.";
  public static final String MSG_PARAMETERS_ERROR = "파라미터가 부족합니다.";

  // Member
  public static final String MSG_WRONG_PASSWORD_ERROR = "회원 정보가 없거나 패스워드가 일치하지 않습니다.";
  public static final String MSG_CURRENT_PASSWORD_ERROR = "패스워드 변경시 현재 패스워드가 필요합니다.";
  public static final String MSG_CURRENT_PASSWORD_MATCHING_ERROR = "현재 비밀번호가 일치하지 않습니다.";
  public static final String MSG_PASSWORD_MATCHING_ERROR = "비밀번호와 확인 비밀번호가 다릅니다.";
  public static final String MSG_NOT_MATCH_PASSWORD = "유효한 패스워드가 아닙니다.";
  public static final String MSG_NOT_MATCH_NAME = "유효한 이름이 아닙니다.";
  public static final String MSG_NOT_MATCH_EMAIL = "유효한 메일주소가 아닙니다.";
  public static final String MSG_PASSWORD_SAME_ERROR = "신규 비밀번호와 현재 비밀번호가 동일합니다.";
  public static final String MSG_MEMBER_NO = "존재하지 않는 회원입니다.";
  public static final String MSG_TOKEN_ERROR = "토큰이 유효하지 않습니다.";
  public static final String MSG_VALUE_ERROR = "값이 유효하지 않습니다.";
  public static final String MSG_ALREADY_VERIFIED_EMAIL = "이미 메일 인증이 되었습니다.";
  public static final String MSG_NOT_VERIFIED_EMAIL = "메일 인증이 되지 않았습니다.";
  public static final String MSG_ALREADY_VERIFIED_SMS = "이미 SMS 인증이 되었습니다.";
  public static final String MSG_VERIFY_CONTENT_SMS = "From NFTMOA: %s is your verification code.\nIf you didn’t request this code, please contact us on information@labshigh.io";


  // 계정 종류 (1:email, 2:metamask)
  public static final int MAX_KIND_INPUT = 2;

  // Community Board
  public static final int MAX_COMMUNITY_TITLE_LENGTH = 500;

  public static final String MSG_COMMUNITY_SEARCH_ERROR = "검색컬럼 또는 검색어를 입력해주세요.";
  public static final String MSG_COMMUNITY_ALREADY_MEMBER = "이미 해당 Group 에 Member 입니다.";
  public static final String MSG_COMMUNITY_ALREADY_INVITE = "이미 해당 Group 에 초대 된 회원입니다.";
  public static final String MSG_COMMUNITY_NO_MEMBER = "해당 Group 에 Member 가 아닙니다.";
  public static final String MSG_COMMUNITY_INVITE_SAME_MEMBER = "본인은 초대 할수가 없습니다.";

  // Market
  public static final int MAX_MARKET_NAME_LENGTH = 100;
  public static final String MSG_MARKET_NOT_MY_MARKET = "본인이 생성한 Market 에만 등록이 가능합니다.";
  public static final String MSG_MARKET_ITEM_MIN_LENGTH_OVER_ERROR = "%s 는 최소 1개 이상 입력해야합니다.";

  // MarketItem2
  public static final String MSG_MARKET_ITEM2_EDIT_ERROR = "수정이 불가능한 아이템입니다.";
  public static final String MSG_MARKET_ITEM2_LOCK_ERROR = "잠금되어 있는 아이템은 수정 여부 업데이트가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_DELETE_ERROR = "삭제가 불가능한 아이템입니다.";
  public static final String MSG_MARKET_ITEM2_LOCK_DELETE_ERROR = "잠금되어 있는 아이템은 삭제가 불가능합니다.";

  public static final String MSG_MARKET_ITEM2_CURRENT_QUANTITY_ERROR = "남은 수량보다 판매하려는 수량이 많을 수 없습니다.";
  public static final String MSG_MARKET_ITEM2_BUY_CURRENT_QUANTITY_ERROR = "판매 수량보다 구매하려는 수량이 많을 수 없습니다.";

  public static final String MSG_MARKET_ITEM2_SELL_QUANTITY_ERROR = "발매 수량이 구매 한 수량보다 많을 수 없습니다.";
  public static final String MSG_MARKET_ITEM2_BUY_MINTING_STATUS_ERROR = "민팅되지 않은 아이템입니다..";
  public static final String MSG_MARKET_ITEM2_MEMBER_ERROR = "본인이 생성 한 아이템만 판매가 가능합니다.";
  public static final String MSG_MARKET_ITEM2_ITEM_KIND_ERROR = "본인이 생성 한 아이템만 수정 가능합니다.";
  public static final String MSG_MARKET_ITEM2_MY_ITEM_BUY_ERROR = "본인이 생성 한 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_END_DATE_BUY_ERROR = "판매가 마감 된 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM2_REPORT_MEMBER_ERROR = "본인이 판매중인 아이템은 신고 할 수 없습니다.";
  // ShortUrl
  public static final String MSG_COMMUNITY_WRONG_SHORTURL = "잘못된 ShortUrl 입니다.";
  //Scam Board
  public static final String MSG_WRONG_URL = "올바르지 않은 Url 입니다.";
  public static final String MSG_SCAM_BOARD_OPINION_NAME_ERROR = "Opinion 이름이 올바르지 않습니다.";
  public static final String MSG_SCAM_BOARD_OPINION_INCREMENT_ERROR = "증가 값이 잘못 됐습니다.";
  public static final String MSG_SCAM_BOARD_OPTINON_DUPLICATE_ERROR = "이미 체크 하였습니다.";
  // Market Comment
  public static final String MSG_MARKET_COMMENT_NOT_MY_COMMENT = "본인이 작성한 코멘트만 수정, 삭제가 가능합니다.";
  // Item
  public static final String MSG_ITEM_QUANTITY_ERROR = "수량이 0 이하 일수 없습니다.";
  public static final String MSG_ITEM_MEMBER_ERROR = "본인이 생성 한 아이템만 판매가 가능합니다.";
  public static final String MSG_ITEM_BUY_CURRENT_QUANTITY_ERROR = "판매 수량보다 구매하려는 수량이 많을 수 없습니다.";

  //Market Item
  public static final String MSG_MARKET_ITEM_BUY_MINTING_STATUS_ERROR = "민팅되지 않은 아이템입니다..";
  public static final String MSG_MARKET_ITEM_MY_ITEM_BUY_ERROR = "본인이 생성 한 아이템은 구매가 불가능합니다.";
  public static final String MSG_MARKET_ITEM_END_DATE_BUY_ERROR = "판매가 마감 된 아이템은 구매가 불가능합니다.";

  private Constants() {
    throw new IllegalStateException("Constants Class");
  }
}
