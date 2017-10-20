package com.bric.kagdatabkt.net;

import android.util.Log;

import com.bric.kagdatabkt.entry.ChitanglistResult;
import com.bric.kagdatabkt.entry.DanganlistResult;
import com.bric.kagdatabkt.entry.ImageResult;
import com.bric.kagdatabkt.entry.LunboResult;
import com.bric.kagdatabkt.entry.ProductResult;
import com.bric.kagdatabkt.entry.QiyeResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.entry.WeishiImageResult;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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

    @POST("Api4Users/findPasswordStepOne")
    Observable<ResultEntry> doForgetPassword_1(
            @Query("username") String username,
            @Query("mobile_code") String mobile_code
    );

    @POST("Api4Users/findPasswordStepTwo")
    Observable<ResultEntry> doForgetPassword_2(
            @Query("username") String username,
            @Query("password") String password,
            @Query("repassword") String repassword
    );

    @POST("Api4Users/updatePassword")
    Observable<ResultEntry> doUpdatePassword(
            @Query("userid") String userid,
            @Query("access_token") String access_token,
            @Query("oldpassword") String oldpassword,
            @Query("newpassword") String newpassword,
            @Query("renewpassword") String renewpassword
    );

    @POST("Api4Users/add_user_info")
    Observable<ResultEntry> doAdd_user_info(
            @Query("access_token") String access_token,
            @Query("company_name") String company_name,
            @Query("company_profile") String company_profile,
            @Query("company_qualification") String company_qualification,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Users/get_user_info")
    Observable<QiyeResult> doGet_user_info(
            @Query("access_token") String access_token
    );

    @POST("Api4Datas/get_breeding_gardens")
    Observable<ChitanglistResult> doGet_breeding_gardens(
            @Query("access_token") String access_token
    );

    @POST("Api4Datas/get_jobs")
    Observable<DanganlistResult> doGet_jobs(
            @Query("access_token") String access_token,
            @Query("garden_numid") String garden_numid,
            @Query("job_type_id") int job_type_id,
            @Query("page") String page,
            @Query("limit") String limit

    );

    @POST("Api4Datas/get_breed_products")
    Observable<ProductResult> doGet_breed_products(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("job_type_id") String job_type_id

    );

    @POST("Api4Datas/get_apply_qrcode_list")
    Observable<QrcodeListResult> doGet_apply_qrcode_list(
            @Query("access_token") String access_token
    );

    @POST("api4Datas/get_supplier_carousels")
    Observable<LunboResult> doGet_supplier_carousels(
    );

    @POST("Api4Ponds/add_reeding_garden")
    Observable<ResultEntry> doAdd_reeding_garden(
            @Query("access_token") String access_token,
            @Query("garden_name") String garden_name,
            @Query("garden_address") String garden_address,
            @Query("lat") String lat,
            @Query("lng") String lng,
            @Query("garden_area") String garden_area,
            @Query("garden_charge") String garden_charge,
            @Query("garden_tel") String garden_tel,
            @Query("garden_profile") String garden_profile,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_disease_prevention")
    Observable<ResultEntry> doAdd_job_disease_prevention(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("supplies") String supplies,
            @Query("operator") String operator,
            @Query("remark") String remark,
            @Query("consumption") String consumption,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_seedling")
    Observable<ResultEntry> doAdd_job_seedling(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("product_id") int product_id,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("source") String source,
            @Query("operator") String operator,
            @Query("remark") String remark,
            @Query("consumption") String consumption,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_feed")
    Observable<ResultEntry> doAdd_job_feed(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("consumption") String consumption,
            @Query("operator") String operator,
            @Query("feed_pic") String feed_pic,
            @Query("feed_name") String feed_name,
            @Query("buyer") String buyer,
            @Query("remark") String remark,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_fishing")
    Observable<ResultEntry> doAdd_job_fishing(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("product_id") int product_id,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("consumption") String consumption,
            @Query("operator") String operator,
            @Query("remark") String remark,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_testing")
    Observable<ResultEntry> doAdd_job_testing(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("product_id") int product_id,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("project_name") String project_name,
            @Query("result") int result,
            @Query("operator") String operator,
            @Query("remark") String remark,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_daily")
    Observable<ResultEntry> doAdd_job_daily(
            @Query("access_token") String access_token,
            @Query("filebag_numid") String filebag_numid,
            @Query("title") String title,
            @Query("control_date") String control_date,
            @Query("daily_type_id") int daily_type_id,
            @Query("consumption") String consumption,
            @Query("operator") String operator,
            @Query("remark") String remark,
            @Query("file_urls") String file_urls
    );

    @POST("Api4Aquatics/add_reeding_garden_pics")
    Observable<ImageResult> doAdd_reeding_garden_pics(@Body RequestBody Body);

    @POST("Api4Aquatics/add_job_pics")
    Observable<ImageResult> doAdd_job_pics(@Body RequestBody Body);

    @POST("Api4Aquatics/add_job_feed_pics")
    Observable<WeishiImageResult> doAdd_job_feed_pics(@Body RequestBody Body);

    //    @Multipart
    @POST("Api4Aquatics/add_user_info_pics")
    Observable<ImageResult> doAdd_user_info_pics(@Body RequestBody Body);


    public class ServiceManager {
        private volatile static ServiceManager serviceManager;
        static final int DEFAULT_CONNECT_TIMEOUT = 16;
        static final int DEFAULT_READ_TIMEOUT = 150;
        static final String EPG_BASE_DOMAIN = "http:///nma.yy/";
        static final String IMAGE_BASE_DOMAIN = "http://nmu.yy/";
        private RetrofitHelper baseService;
        private RetrofitHelper imageService;
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
            Retrofit epgRetrofit = new Retrofit.Builder()
                    .baseUrl(EPG_BASE_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mClient)
                    .build();
            Retrofit imageRetrofit = new Retrofit.Builder()
                    .baseUrl(IMAGE_BASE_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mClient)
                    .build();
            baseService = epgRetrofit.create(RetrofitHelper.class);
            imageService = imageRetrofit.create(RetrofitHelper.class);
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

        public static RetrofitHelper getBaseImageService() {

            return getInstance().imageService;

        }
    }
}
