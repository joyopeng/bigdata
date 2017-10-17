package com.bric.kagdatabkt.utils;

import com.blankj.utilcode.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by joyopeng on 17-9-14.
 */

public class CommonConstField {
    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static final String DANGAN_CONTENT_TYPE_KEY = "operator_key";
    public static final int DANGAN_CONTENT_TYPE_XIAODU = 1;
    public static final int DANGAN_CONTENT_TYPE_TOUMIAO = 2;
    public static final int DANGAN_CONTENT_TYPE_WEISHI = 3;
    public static final int DANGAN_CONTENT_TYPE_BURAO = 4;
    public static final int DANGAN_CONTENT_TYPE_JIANCE = 5;
    public static final int DANGAN_CONTENT_TYPE_FANGSHUI = 6;
    //tab id
    public static final int TABHOST_TAB_HOMEPAGE_ID = 1;
    public static final int TABHOST_TAB_CHITANG_ID = 2;
    public static final int TABHOST_TAB_DANGAN_ID = 3;
    public static final int TABHOST_TAB_SETTING_ID = 2;

    //prefrence
    public static final String COMMON_PREFRENCE = "common_preference";
    public static final String LOCATION_CITY = "location_city";
    public static final String LOCATION_DISTRICT = "location_district";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";


    public static boolean isMatchered(CharSequence input) {
        if (StringUtils.isEmpty(input))
            return false;
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
