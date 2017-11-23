package com.bric.kagdatabkt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bric.kagdatabkt.entry.ImageResult;
import com.bric.kagdatabkt.entry.QiyeResult;
import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

public class QiyexinxiAdd extends AppCompatActivity {
    private static final String TAG = QiyexinxiAdd.class.getSimpleName();
    private static final int ZIZHI_TAG = 1;
    private static final int RONGYU_TAG = 2;
    private ImageView base_nav_back;
    private ImageView base_nav_right;
    private TextView base_toolbar_title;
    private EditText setting_edit_qiye_name;
    private EditText setting_edit_qiye_zizhi;
    private EditText setting_edit_qiye_jianjie;
    private ImageView setting_qiye_yingyezhizhao_add;
    private ImageView setting_qiye_yingyerongyu_add;
    private LinearLayout yingyezhizhao_upload_image_view;
    private LinearLayout qiyerongyu__upload_image_view;
    private ImageView zhizhao_preview_img1;
    private ImageView zhizhao_preview_img2;
    private ImageView zhizhao_preview_img3;
    private ImageView zhizhao_preview_img4;

    private ImageView qiyerongyu_preview_img1;
    private ImageView qiyerongyu_preview_img2;
    private ImageView qiyerongyu_preview_img3;
    private ImageView qiyerongyu_preview_img4;
    private Button addqiyexinxi;
    private ArrayList<String> imagepath = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiyexinxi_add);
        initview();
        initActionListener();
        fetchData();
    }

    private void initview() {
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        base_nav_right = (ImageView) findViewById(R.id.base_nav_right);
        base_nav_right.setVisibility(View.GONE);
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setText("个人信息");
        base_toolbar_title.setCompoundDrawables(null, null, null, null);
        setting_edit_qiye_name = (EditText) findViewById(R.id.setting_edit_qiye_name);
        setting_edit_qiye_name.setHint(R.string.setting_hint_qiye_name);
        setting_edit_qiye_name.setBackgroundResource(0);
        setting_edit_qiye_zizhi = (EditText) findViewById(R.id.setting_edit_qiye_zizhi);
        setting_edit_qiye_zizhi.setHint(R.string.setting_hint_qiye_zizhi);
        setting_edit_qiye_zizhi.setBackgroundResource(0);
        setting_edit_qiye_jianjie = (EditText) findViewById(R.id.setting_edit_qiye_jianjie);
        setting_edit_qiye_jianjie.setHint(R.string.setting_hint_qiye_jianjie);
        setting_edit_qiye_jianjie.setBackgroundResource(0);
        setting_qiye_yingyezhizhao_add = (ImageView) findViewById(R.id.setting_qiye_yingyezhizhao_add);
        setting_qiye_yingyerongyu_add = (ImageView) findViewById(R.id.setting_qiye_yingyerongyu_add);
        yingyezhizhao_upload_image_view = (LinearLayout) findViewById(R.id.yingyezhizhao_upload_image_view);
        qiyerongyu__upload_image_view = (LinearLayout) findViewById(R.id.qiyerongyu__upload_image_view);
        zhizhao_preview_img1 = (ImageView) findViewById(R.id.zhizhao_preview_img1);
        zhizhao_preview_img2 = (ImageView) findViewById(R.id.zhizhao_preview_img2);
        zhizhao_preview_img3 = (ImageView) findViewById(R.id.zhizhao_preview_img3);
        zhizhao_preview_img4 = (ImageView) findViewById(R.id.zhizhao_preview_img4);
        qiyerongyu_preview_img1 = (ImageView) findViewById(R.id.qiyerongyu_preview_img1);
        qiyerongyu_preview_img2 = (ImageView) findViewById(R.id.qiyerongyu_preview_img2);
        qiyerongyu_preview_img3 = (ImageView) findViewById(R.id.qiyerongyu_preview_img3);
        qiyerongyu_preview_img4 = (ImageView) findViewById(R.id.qiyerongyu_preview_img4);
        addqiyexinxi = (Button) findViewById(R.id.addqiyexinxi);
        addqiyexinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    Log.v(TAG, file_urls);
//                                    ArrayList<String> files = item.file_url;
                                    SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                                    String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
                                    String cp_name = setting_edit_qiye_name.getText().toString();
                                    String cp_profile = setting_edit_qiye_jianjie.getText().toString();
                                    String cp_qualification = setting_edit_qiye_zizhi.getText().toString();
                                    RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_user_info(access_token, cp_name, cp_profile, cp_qualification, file_urls).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable arg0) {
                                            Log.v(TAG, arg0.getLocalizedMessage());
                                            showError(arg0.getLocalizedMessage());
                                        }

                                        @Override
                                        public void onNext(ResultEntry arg0) {
                                            if (arg0.success == 0) {
                                                finish();
                                            } else {
                                                showError(arg0.message);
                                            }
                                            Log.v(TAG, "message = " + arg0.message);
                                        }
                                    });
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
            }
        });
    }

    private void initActionListener() {
        setting_qiye_yingyezhizhao_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(QiyexinxiAdd.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, ZIZHI_TAG);
            }
        });

        setting_qiye_yingyerongyu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(QiyexinxiAdd.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, RONGYU_TAG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
            imagepath = paths;
            setImageViewLayout(paths, requestCode, true);
        }
    }

    private void setImageViewLayout(ArrayList<String> paths, int requestCode, boolean islocalfile) {
        if (requestCode == ZIZHI_TAG) {
            if (paths.size() > 0)
                yingyezhizhao_upload_image_view.setVisibility(View.VISIBLE);
            if (islocalfile) {
                for (int i = 0; i < paths.size(); i++) {
                    if (i == 0) {
                        Picasso.with(this).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img1);
                    }
                    if (i == 1) {
                        Picasso.with(this).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img2);
                    }
                    if (i == 2) {
                        Picasso.with(this).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img3);
                    }
                    if (i == 3) {
                        Picasso.with(this).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img4);
                    }
                }
            } else {
                for (int i = 0; i < paths.size(); i++) {
                    if (i == 0) {
                        Picasso.with(this).load(paths.get(i)).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img1);
                    }
                    if (i == 1) {
                        Picasso.with(this).load(paths.get(i)).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img2);
                    }
                    if (i == 2) {
                        Picasso.with(this).load(paths.get(i)).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img3);
                    }
                    if (i == 3) {
                        Picasso.with(this).load(paths.get(i)).fit().config(Bitmap.Config.RGB_565).into(zhizhao_preview_img4);
                    }
                }
            }

        } else {
            if (paths.size() > 0)
                qiyerongyu__upload_image_view.setVisibility(View.VISIBLE);
            for (int i = 0; i < paths.size(); i++) {
                if (i == 0) {
                    qiyerongyu_preview_img1.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 1) {
                    qiyerongyu_preview_img2.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 2) {
                    qiyerongyu_preview_img3.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 3) {
                    qiyerongyu_preview_img4.setImageURI(Uri.fromFile(new File(paths.get(i))));
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
        return RetrofitHelper.ServiceManager.getBaseImageService(getApplicationContext()).doAdd_user_info_pics(requestBody);
    }

    private void fetchData() {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGet_user_info(access_token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<QiyeResult>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(QiyeResult arg0) {
                QiyeResult.Item item = arg0.data.get(0);
                setting_edit_qiye_name.setText(item.user_info.company_name);
                setting_edit_qiye_zizhi.setText(item.user_info.company_qualification);
                setting_edit_qiye_jianjie.setText(item.user_info.company_profile);
                if (imagepath != null)
                    imagepath.clear();
                for (QiyeResult.SubItem i : item.reports) {
                    imagepath.add(i.AqUserInfoReport.file_url);
                }
                setImageViewLayout(imagepath, ZIZHI_TAG, false);
            }
        });
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
