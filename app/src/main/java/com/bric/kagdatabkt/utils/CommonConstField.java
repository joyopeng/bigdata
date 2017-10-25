package com.bric.kagdatabkt.utils;

import android.util.Log;

import com.blankj.utilcode.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by joyopeng on 17-9-14.
 */

public class CommonConstField {
    //你们用13776019930 036031
    public final String TAG = CommonConstField.class.getSimpleName();
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

    public static final String NUMID_KEY = "numid_key";
    public static final String JOB_TYPE_ID_KEY = "job_type_id_key";
    public static final String JOB_ID = "job_id";
    public static final String JOB_FISHING_ID = "job_fishing_id";

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

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }
    };

    private static void test() {

        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
        
    }

    public static void main(String[] args) {
        test();
    }
}
