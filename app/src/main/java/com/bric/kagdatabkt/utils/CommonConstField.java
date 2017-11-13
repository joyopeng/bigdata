package com.bric.kagdatabkt.utils;

import android.os.Looper;
import android.util.Log;
import android.util.LruCache;

import com.blankj.utilcode.utils.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
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
    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";

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

    //error code
    public static final int TOKEN_EXPIRED = -999;
    //prefrence
    public static final String COMMON_PREFRENCE = "common_preference";
    public static final String LOCATION_CITY = "location_city";
    public static final String LOCATION_DISTRICT = "location_district";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String APP_KEY = "appkey";

    public static final String NUMID_KEY = "numid_key";
    public static final String NUMNAME_KEY = "numname_key";
    public static final String JOB_TYPE_ID_KEY = "job_type_id_key";
    public static final String JOB_ID = "job_id";
    public static final String JOB_FISHING_ID = "job_fishing_id";

    public static boolean isMatchered(CharSequence input) {
        if (StringUtils.isEmpty(input))
            return false;
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(input);
        if (input.length() == 11) {
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

    private void testUrl() {
        try {
            URL url = new URL("");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("");
//            con.setChunkedStreamingMode();
            InputStream ino = con.getInputStream();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = ino.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//                out.flush();
//            }
            StringBuffer sb = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(ino);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

        } catch (MalformedURLException e) {
        } catch (IOException ioe) {
        }
    }

    public static void main(String[] args) {
//        test();
//        LruCache

        LinkedHashMap<Integer, String> ddd = new LinkedHashMap<>();
        ddd.put(1, "dd");
        ddd.put(1, "dd1");
        ddd.put(1, "dd2");
        System.out.print(ddd);
    }
}
