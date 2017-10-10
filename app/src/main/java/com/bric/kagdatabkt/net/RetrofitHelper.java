package com.bric.kagdatabkt.net;

import android.util.Log;

import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by joyopeng on 17-9-29.
 */

public interface RetrofitHelper {

    @POST("Api4Users/getCaptImg")
    Observable<ResponseBody> doGetQrcode(
            @Query("username") String username,
            @Query("type") String type
    );

    @POST("Api4Users/sendMsg")
    Observable<ResultEntry> doSendMsg(
            @Query("username") String username,
            @Query("Captcha") String Captcha,
            @Query("type") String type
    );

    @POST("Api4Users/register")
    Observable<RegisterResult> doRegister(
            @Query("username") String username,
            @Query("password") String password,
            @Query("mobile_code") String mobile_code,
            @Query("city") String city,
            @Query("district") String district
    );

    @POST("Api4Users/login")
    Observable<RegisterResult> doLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    public class ServiceManager {
        private volatile static ServiceManager serviceManager;
        static final int DEFAULT_CONNECT_TIMEOUT = 6;
        static final int DEFAULT_READ_TIMEOUT = 15;
        String defaultDomain = "http://nma.yy/";
        private RetrofitHelper baseService;
        SSLContext sc = null;
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i("TrustManager", "checkClientTrusted");
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                Log.i("TrustManager", "checkServerTrusted");
            }
        }};

        private ServiceManager() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            SSLContext sc = null;
            try {
                sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            final OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(VodApplication.getHttpParamsInterceptor())
//                    .addNetworkInterceptor(VodApplication.getHttpTrafficInterceptor())
//                    .retryOnConnectionFailure(true)
                    .addInterceptor(interceptor)
                    .sslSocketFactory(sc.getSocketFactory())
                    .build();
            Retrofit upgradeRetrofit = new Retrofit.Builder()
                    .baseUrl(defaultDomain)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mClient)
                    .build();
            baseService = upgradeRetrofit.create(RetrofitHelper.class);
        }

        private static ServiceManager getInstance() {    //对获取实例的方法进行同步
            synchronized (ServiceManager.class) {
                if (serviceManager == null) {
                    serviceManager = new ServiceManager();
                }
            }
            return serviceManager;
        }

        public static RetrofitHelper getBaseService() {

            return getInstance().baseService;
        }
    }

}
