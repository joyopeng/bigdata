package com.bric.kagdatabkt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.bric.kagdatabkt.bean.GardenBean;
import com.bric.kagdatabkt.entry.ImageResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.utils.LocationService;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.ACCESS_TOKEN;

public class ChitangAddActivity extends AppCompatActivity {

    private static final String TAG = ChitangAddActivity.class.getSimpleName();

    private ImageView base_nav_back;
    private MapView mMapView;
    private RelativeLayout rootview;
    private LocationService locationService;
    private MapStatusUpdate mapStatus;
    //view
    private EditText chitang_addpage_name;
    private EditText chitang_addpage_address;
    private EditText chitang_addpage_area;
    private EditText chitang_addpage_owener;
    private EditText chitang_addpage_owenerphone;
    private EditText chitang_addpage_discription_label;

    private ImageView document_media_add;
    private LinearLayout upload_image_view;
    private ImageView preview_img1;
    private ImageView preview_img2;
    private ImageView preview_img3;
    private ImageView preview_img4;

    private Button addchitang_button;
    private ArrayList<String> imagepath;
    private String lat;
    private String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitang_content);
        mMapView = (MapView) findViewById(R.id.chitangaddressmap);
        initView();
    }

    private void initView() {

        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chitang_addpage_name = (EditText) findViewById(R.id.chitang_addpage_name);
        chitang_addpage_address = (EditText) findViewById(R.id.chitang_addpage_address);
        chitang_addpage_area = (EditText) findViewById(R.id.chitang_addpage_area);
        chitang_addpage_owener = (EditText) findViewById(R.id.chitang_addpage_owener);
        chitang_addpage_owenerphone = (EditText) findViewById(R.id.chitang_addpage_owenerphone);
        chitang_addpage_discription_label = (EditText) findViewById(R.id.chitang_addpage_discription_label);
        //
        chitang_addpage_name.setBackgroundResource(0);
        chitang_addpage_address.setBackgroundResource(0);
        chitang_addpage_area.setBackgroundResource(0);
        chitang_addpage_owener.setBackgroundResource(0);
        chitang_addpage_owenerphone.setBackgroundResource(0);
        chitang_addpage_discription_label.setBackgroundResource(0);

        document_media_add = (ImageView) findViewById(R.id.document_media_add);
        upload_image_view = (LinearLayout) findViewById(R.id.upload_image_view);
        preview_img1 = (ImageView) findViewById(R.id.preview_img1);
        preview_img2 = (ImageView) findViewById(R.id.preview_img2);
        preview_img3 = (ImageView) findViewById(R.id.preview_img3);
        preview_img4 = (ImageView) findViewById(R.id.preview_img4);

        document_media_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ChitangAddActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, 2);
            }
        });

        addchitang_button = (Button) findViewById(R.id.addchitang_button);
        addchitang_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagepath != null && imagepath.size() > 0) {
                    ArrayList<File> images = new ArrayList<File>();
                    for (String i : imagepath) {
                        images.add(new File(i));
                    }
                    Observable<ImageResult> entry = upload(images);
                    entry.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ImageResult>() {
                                @Override
                                public void onNext(ImageResult uploadImgBean) {
                                    if (uploadImgBean.data.size() > 0) {
                                        ImageResult.Item item = uploadImgBean.data.get(0);
                                        Gson gson = new Gson();
                                        String file_urls = gson.toJson(item);
                                        submitData(file_urls);
                                    }
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    Log.i(TAG, "onError: --->" + throwable.getMessage());
                                }

                                @Override
                                public void onCompleted() {
                                    Log.i(TAG, "onComplete: ");
                                }
                            });
                } else {
                    submitData("");
                }
            }
        });
    }

    private void submitData(String fileurls) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        GardenBean bean = new GardenBean();
        bean.access_token = access_token;
        bean.garden_name = chitang_addpage_name.getText().toString();
        bean.garden_address = chitang_addpage_address.getText().toString();
        bean.garden_area = chitang_addpage_area.getText().toString();
        bean.garden_charge = chitang_addpage_owener.getText().toString();
        bean.garden_tel = chitang_addpage_owenerphone.getText().toString();
        bean.garden_profile = chitang_addpage_discription_label.getText().toString();
        bean.file_urls = fileurls;
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_reeding_garden(
                bean.access_token, bean.garden_name,
                bean.garden_address, lat, lng, bean.garden_area, bean.garden_charge,
                bean.garden_tel, bean.garden_profile, bean.file_urls).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    ChitangAddActivity.this.finish();
                }
            }
        });
    }

    private Observable<ImageResult> uploadImageData(List<File> fileList) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token);
        int i = 0;
        for (File file : fileList) {
            builder.addFormDataPart("file_url[" + i++ + "]", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        }
        MultipartBody requestBody = builder.build();
        return RetrofitHelper.ServiceManager.getBaseImageService(getApplicationContext()).doAdd_reeding_garden_pics(requestBody);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
            imagepath = paths;
            if (paths.size() > 0)
                upload_image_view.setVisibility(View.VISIBLE);
            for (int i = 0; i < paths.size(); i++) {
                if (i == 0) {
                    Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img1);
                }
                if (i == 1) {
                    Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img2);
                }
                if (i == 2) {
                    Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img3);
                }
                if (i == 3) {
                    Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img4);
                }
            }
        }
    }

    private Observable<ImageResult> upload(List<File> fileList) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token);
        int i = 0;
        for (File file : fileList) {
            builder.addFormDataPart("file_url[" + i++ + "]", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        }
        MultipartBody requestBody = builder.build();
        return RetrofitHelper.ServiceManager.getBaseImageService(getApplicationContext()).doAdd_reeding_garden_pics(requestBody);
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = new LocationService(getApplicationContext());
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                mapStatus = MapStatusUpdateFactory.newLatLngZoom(latlng, 19.0f);
                mMapView.getMap().animateMapStatus(mapStatus);
                chitang_addpage_address.setText(location.getAddrStr());

//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// 定位类型
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// 纬度
//                sb.append(location.getLatitude());
//                sb.append("\nlontitude : ");// 经度
//                sb.append(location.getLongitude());
//                sb.append("\nradius : ");// 半径
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// 国家码
//                sb.append(location.getCountryCode());
//                sb.append("\nCountry : ");// 国家名称
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// 城市编码
//                sb.append(location.getCityCode());
//                sb.append("\ncity : ");// 城市
//                sb.append(location.getCity());
//                sb.append("\nDistrict : ");// 区
//                sb.append(location.getDistrict());
//                sb.append("\nStreet : ");// 街道
//                sb.append(location.getStreet());
//                sb.append("\naddr : ");// 地址信息
//                sb.append(location.getAddrStr());
//                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                sb.append(location.getUserIndoorState());
//                sb.append("\nDirection(not all devices have value): ");
//                sb.append(location.getDirection());// 方向
//                sb.append("\nlocationdescribe: ");
//                sb.append(location.getLocationDescribe());// 位置语义化信息
//                sb.append("\nPoi: ");// POI信息
//                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
//                    for (int i = 0; i < location.getPoiList().size(); i++) {
//                        Poi poi = (Poi) location.getPoiList().get(i);
//                        sb.append(poi.getName() + ";");
//                    }
//                }
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// 速度 单位：km/h
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());// 卫星数目
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// 海拔高度 单位：米
//                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
//                    sb.append("\ndescribe : ");
//                    sb.append("gps定位成功");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                    // 运营商信息
//                    if (location.hasAltitude()) {// *****如果有海拔高度*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// 单位：米
//                    }
//                    sb.append("\noperationers : ");// 运营商信息
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("网络定位成功");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                    sb.append("\ndescribe : ");
//                    sb.append("离线定位成功，离线定位结果也是有效的");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//                }
//                Log.v(TAG, sb.toString());
            }
        }

    };
}
