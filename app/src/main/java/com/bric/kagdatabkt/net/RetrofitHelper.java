package com.bric.kagdatabkt.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bric.kagdatabkt.entry.ChitanglistResult;
import com.bric.kagdatabkt.entry.DanganDetailResult;
import com.bric.kagdatabkt.entry.DanganlistResult;
import com.bric.kagdatabkt.entry.ImageResult;
import com.bric.kagdatabkt.entry.LunboResult;
import com.bric.kagdatabkt.entry.ProductResult;
import com.bric.kagdatabkt.entry.QiyeResult;
import com.bric.kagdatabkt.entry.QrcodeInfoResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.entry.WeishiImageResult;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.bric.kagdatabkt.utils.CommonConstField.ACCESS_TOKEN;
import static com.bric.kagdatabkt.utils.CommonConstField.APP_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.TOKEN_EXPIRED;
import static com.bric.kagdatabkt.utils.CommonConstField.USER_ID;

/**
 * Created by joyopeng on 17-9-29.
 */

public interface RetrofitHelper {

    @POST("Api4Users/getCaptImg")
    @FormUrlEncoded
    Observable<ResponseBody> doGetQrcode(
            @Field("username") String username,
            @Field("type") String type
    );

    @POST("Api4Users/sendMsg")
    @FormUrlEncoded
    Observable<ResultEntry> doSendMsg(
            @Field("username") String username,
            @Field("Captcha") String Captcha,
            @Field("type") String type
    );

    @POST("Api4Users/register")
    @FormUrlEncoded
    Observable<RegisterResult> doRegister(
            @Field("username") String username,
            @Field("password") String password,
            @Field("mobile_code") String mobile_code,
            @Field("city") String city,
            @Field("district") String district
    );

    @POST("Api4Users/login")
    @FormUrlEncoded
    Observable<RegisterResult> doLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("Api4Users/refresh_token")
    @FormUrlEncoded
    Call<RegisterResult> doRefresh_token(
            @Field("user_id") String user_id,
            @Field("appkey") String appkey
    );

    @POST("Api4Users/findPasswordStepOne")
    @FormUrlEncoded
    Observable<ResultEntry> doForgetPassword_1(
            @Field("username") String username,
            @Field("mobile_code") String mobile_code
    );

    @POST("Api4Users/findPasswordStepTwo")
    @FormUrlEncoded
    Observable<ResultEntry> doForgetPassword_2(
            @Field("username") String username,
            @Field("password") String password,
            @Field("repassword") String repassword
    );

    @POST("Api4Users/updatePassword")
    @FormUrlEncoded
    Observable<ResultEntry> doUpdatePassword(
            @Field("userid") String userid,
            @Field("access_token") String access_token,
            @Field("oldpassword") String oldpassword,
            @Field("newpassword") String newpassword,
            @Field("renewpassword") String renewpassword
    );

    @POST("Api4Users/add_user_info")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_user_info(
            @Field("access_token") String access_token,
            @Field("company_name") String company_name,
            @Field("company_profile") String company_profile,
            @Field("company_qualification") String company_qualification,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Users/get_user_info")
    @FormUrlEncoded
    Observable<QiyeResult> doGet_user_info(
            @Field("access_token") String access_token
    );

    @POST("Api4Datas/get_breeding_gardens")
    @FormUrlEncoded
    Observable<ChitanglistResult> doGet_breeding_gardens(
            @Field("access_token") String access_token
    );

    @POST("Api4Datas/get_jobs")
    @FormUrlEncoded
    Observable<DanganlistResult> doGet_jobs(
            @Field("access_token") String access_token,
            @Field("garden_numid") String garden_numid,
            @Field("job_type_id") int job_type_id,
            @Field("page") int page,
            @Field("limit") int limit

    );

    @POST("Api4Datas/get_job_info")
    @FormUrlEncoded
    Observable<DanganDetailResult> doGet_job_info(
            @Field("access_token") String access_token,
            @Field("garden_numid") String garden_numid,
            @Field("job_type_id") int job_type_id,
            @Field("id") int id
    );

    @POST("Api4Datas/get_breed_products")
    @FormUrlEncoded
    Observable<ProductResult> doGet_breed_products(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("job_type_id") String job_type_id

    );

    @POST("Api4Datas/get_apply_qrcode_list")
    @FormUrlEncoded
    Observable<QrcodeListResult> doGet_apply_qrcode_list(
            @Field("access_token") String access_token
    );

    @POST("Api4Datas/get_apply_qrcode_info")
    @FormUrlEncoded
    Observable<QrcodeInfoResult> doGet_apply_qrcode_list(
            @Field("access_token") String access_token,
            @Field("job_fishing_id") int job_fishing_id
    );

    @POST("api4Datas/get_supplier_carousels")
    @FormUrlEncoded
    Observable<LunboResult> doGet_supplier_carousels(
            @Field("access_token") String access_token
    );

    @POST("Api4Ponds/add_reeding_garden")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_reeding_garden(
            @Field("access_token") String access_token,
            @Field("garden_name") String garden_name,
            @Field("garden_address") String garden_address,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("garden_area") String garden_area,
            @Field("garden_charge") String garden_charge,
            @Field("garden_tel") String garden_tel,
            @Field("garden_profile") String garden_profile,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_disease_prevention")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_disease_prevention(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("supplies") String supplies,
            @Field("operator") String operator,
            @Field("remark") String remark,
            @Field("consumption") String consumption,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_seedling")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_seedling(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("product_id") int product_id,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("source") String source,
            @Field("operator") String operator,
            @Field("remark") String remark,
            @Field("consumption") String consumption,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_feed")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_feed(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("consumption") String consumption,
            @Field("operator") String operator,
            @Field("feed_pic") String feed_pic,
            @Field("feed_name") String feed_name,
            @Field("buyer") String buyer,
            @Field("remark") String remark,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_fishing")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_fishing(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("product_id") int product_id,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("consumption") String consumption,
            @Field("operator") String operator,
            @Field("remark") String remark,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_testing")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_testing(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("product_id") int product_id,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("project_name") String project_name,
            @Field("result") int result,
            @Field("operator") String operator,
            @Field("remark") String remark,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/add_job_daily")
    @FormUrlEncoded
    Observable<ResultEntry> doAdd_job_daily(
            @Field("access_token") String access_token,
            @Field("filebag_numid") String filebag_numid,
            @Field("title") String title,
            @Field("control_date") String control_date,
            @Field("daily_type_id") int daily_type_id,
            @Field("consumption") String consumption,
            @Field("operator") String operator,
            @Field("remark") String remark,
            @Field("file_urls") String file_urls
    );

    @POST("Api4Ponds/apply_qrcode")
    @FormUrlEncoded
    Observable<ResultEntry> doApply_qrcode(
            @Field("access_token") String access_token,
            @Field("job_fishing_id") int job_fishing_id,
            @Field("quantity") int quantity
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
        static Context content = null;
        private volatile static ServiceManager serviceManager;
        static final int DEFAULT_CONNECT_TIMEOUT = 16;
        static final int DEFAULT_READ_TIMEOUT = 150;
        //        static final String EPG_BASE_DOMAIN = "http:///nma.yy/";
//        static final String IMAGE_BASE_DOMAIN = "http://nmu.yy/";
        static final String EPG_BASE_DOMAIN = "http://118.190.66.64:10001/";
        static final String IMAGE_BASE_DOMAIN = "http://118.190.66.64:10003/";
        private RetrofitHelper baseService;
        private RetrofitHelper imageService;
        private RetrofitHelper freshService;
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
//                    .addNetworkInterceptor(new TokenExpired(content))
//                    .retryOnConnectionFailure(true)
                    .addInterceptor(new TokenExpired(content))
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
            Retrofit freshRetrofit = new Retrofit.Builder()
                    .baseUrl(EPG_BASE_DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .client(mClient)
                    .build();
            baseService = epgRetrofit.create(RetrofitHelper.class);
            imageService = imageRetrofit.create(RetrofitHelper.class);
            freshService = freshRetrofit.create(RetrofitHelper.class);
        }

        private static ServiceManager getInstance() {    //对获取实例的方法进行同步
            synchronized (ServiceManager.class) {
                if (serviceManager == null) {
                    serviceManager = new ServiceManager();
                }
            }
            return serviceManager;
        }

        public static RetrofitHelper getBaseService(Context baseContent) {
            if (content == null) {
                content = baseContent;
            }
            return getInstance().baseService;
        }

        public static RetrofitHelper getBaseImageService(Context baseContent) {
            if (content == null) {
                content = baseContent;
            }
            return getInstance().imageService;
        }

        public static RetrofitHelper getBaseFreshService() {
            return getInstance().freshService;
        }
    }

    public class TokenExpired implements Interceptor {
        Context context;

        public TokenExpired(Context c) {
            context = c;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (request.url().toString().contains("getCaptImg")) {
                return chain.proceed(request);
            }
            Response response = chain.proceed(request);
            okhttp3.Headers headers = response.headers();
            ResponseBody originbody = response.body();
            BufferedSource source = originbody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = originbody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String content = buffer.clone().readString(charset);
            Gson g = new Gson();
            ResultEntry entry = g.fromJson(content, ResultEntry.class);
            int errorcode = entry.success;
            if (errorcode == TOKEN_EXPIRED) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                String userid = sharedPreferences.getString(USER_ID, "");
                String appkey = sharedPreferences.getString(APP_KEY, "");
                Call<RegisterResult> call = RetrofitHelper.ServiceManager.getBaseFreshService().doRefresh_token(userid, appkey);
                retrofit2.Response<RegisterResult> response1 = call.execute();
                RegisterResult result = response1.body();
                String token = result.data.get(0).Token.access_token;
                sharedPreferences.edit().putString(ACCESS_TOKEN, token).commit();
                FormBody oidFormBody = (FormBody) request.body();
                FormBody.Builder newFormBody = new FormBody.Builder();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    if (!"access_token".equals(oidFormBody.encodedName(i))) {
                        newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                    } else {
                        newFormBody.addEncoded(oidFormBody.encodedName(i), token);
                    }
                }
                Request newRequest = request.newBuilder().method(request.method(), newFormBody.build()).build();
                response.close();
                return chain.proceed(newRequest);
            }
            return response;
        }
    }
}
