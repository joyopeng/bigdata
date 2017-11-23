package com.bric.kagdatabkt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.id.input;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText phonenumber;
    private EditText verify_code;
    private EditText register_password;
    private Button register_button;
    private ImageView base_nav_back;
    private ImageView base_nav_right;
    private TextView login_forgetpassword;
    private TextView login_register;
    private Button button_getqrcode;
    private TextView base_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
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
        base_nav_right = (ImageView) findViewById(R.id.base_nav_right);
        base_nav_right.setVisibility(View.GONE);
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setText("注册");
        base_toolbar_title.setCompoundDrawables(null, null, null, null);
        phonenumber = (EditText) findViewById(R.id.register_phonenumber);
        verify_code = (EditText) findViewById(R.id.verify_code);
        register_password = (EditText) findViewById(R.id.register_password);
        phonenumber.setHint(R.string.hint_phonenumber);
        phonenumber.setBackgroundResource(0);
        verify_code.setHint(R.string.hint_identifying_code);
        verify_code.setBackgroundResource(0);
        register_password.setHint(R.string.hint_passwod);
        register_password.setBackgroundResource(0);
        button_getqrcode = (Button) findViewById(R.id.button_getqrcode);
        button_getqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = phonenumber.getText().toString();
                String password = register_password.getText().toString();
                String mobile_code = verify_code.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile_code)) {
                    showError("填写完整的注册信息");
                    return;
                }
                String city = sharedPreferences.getString(CommonConstField.LOCATION_CITY, "苏州");
                String district = sharedPreferences.getString(CommonConstField.LOCATION_DISTRICT, "相城区");
                RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doRegister(username, password, mobile_code, city, district)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new Observer<RegisterResult>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable arg0) {
                                Log.v(TAG, arg0.getLocalizedMessage());
                                showError(arg0.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(RegisterResult arg0) {
                                if (arg0.data.size() > 0) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                                    String access_token = ((RegisterResult.Item) (arg0.data.get(0))).Token.access_token;
                                    String user_id = ((RegisterResult.Item) (arg0.data.get(0))).User.id;
                                    String appkey = ((RegisterResult.Item) (arg0.data.get(0))).User.appkey;
                                    sharedPreferences.edit().putString(CommonConstField.ACCESS_TOKEN, access_token).commit();
                                    sharedPreferences.edit().putString(CommonConstField.USER_ID, user_id).commit();
                                    sharedPreferences.edit().putString(CommonConstField.USER_NAME, username).commit();
                                    sharedPreferences.edit().putString(CommonConstField.APP_KEY, appkey).commit();
                                    Intent registerintent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(registerintent);
                                    finish();
                                } else {
                                    showError(arg0.message);
                                }
                            }
                        }
                );
            }
        });
    }

    private void showDialog() {
        if (CommonConstField.isMatchered(phonenumber.getText().toString())) {
            final CustomDialog dialog = new CustomDialog(this);
            dialog.show();
        } else {
            Toast.makeText(this, "请填写正确的手机号码", Toast.LENGTH_LONG).show();
        }
    }


    class CustomDialog extends Dialog {

        EditText qrcode_edit;
        ImageView qrcode_image;
        TextView reget_qrcode;
        Button doubleLeftBtn;
        Button doubleRightBtn;

        public CustomDialog(Context context) {
            super(context, R.style.CustomDialogStyle);
        }

        @Override
        public void show() {
            super.show();
            RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGetQrcode(phonenumber.getText().toString(), "").subscribeOn(Schedulers.newThread())
                    .map(new Func1<ResponseBody, Bitmap>() {
                        @Override
                        public Bitmap call(ResponseBody arg0) {
                            Bitmap bitmap = BitmapFactory.decodeStream(arg0.byteStream());
                            return bitmap;//返回一个bitmap对象
                        }
                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new Subscriber<Bitmap>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable arg0) {
                        }

                        @Override
                        public void onNext(Bitmap arg0) {
                            qrcode_image.setImageBitmap(arg0);
                        }
                    }
            );
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setCancelable(false);  // 是否可以撤销
            setContentView(R.layout.getqrcode);
            qrcode_edit = (EditText) findViewById(R.id.qrcode_edit);
            qrcode_image = (ImageView) findViewById(R.id.qrcode_image);
            reget_qrcode = (TextView) findViewById(R.id.reget_qrcode);
            doubleLeftBtn = (Button) findViewById(R.id.btn_common_dialog_double_left);
            doubleRightBtn = (Button) findViewById(R.id.btn_common_dialog_double_right);

            reget_qrcode.setOnClickListener(clickListener);
            doubleLeftBtn.setOnClickListener(clickListener);
            doubleRightBtn.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.reget_qrcode: {
                        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGetQrcode(phonenumber.getText().toString(), "").subscribeOn(Schedulers.newThread())
                                .map(new Func1<ResponseBody, Bitmap>() {
                                    @Override
                                    public Bitmap call(ResponseBody arg0) {
                                        Bitmap bitmap = BitmapFactory.decodeStream(arg0.byteStream());
                                        return bitmap;//返回一个bitmap对象
                                    }
                                }).observeOn(AndroidSchedulers.mainThread()).subscribe(
                                new Subscriber<Bitmap>() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                    }

                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable arg0) {
                                    }

                                    @Override
                                    public void onNext(Bitmap arg0) {
                                        qrcode_image.setImageBitmap(arg0);
                                    }
                                }
                        );
                    }
                    break;
                    case R.id.btn_common_dialog_double_left: {
                        CustomDialog.this.dismiss();
                    }
                    break;
                    case R.id.btn_common_dialog_double_right: {
                        if (StringUtils.isEmpty(qrcode_edit.getText().toString())) {
                            showError("请填写验证码");
                            return;
                        }
                        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doSendMsg(phonenumber.getText().toString(), qrcode_edit.getText().toString(), "reg")
                                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                                new Observer<ResultEntry>() {
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
                                            CustomDialog.this.dismiss();
                                        } else {
                                            showError(arg0.message);
                                        }
                                    }
                                }
                        );
                    }
                    break;
                }
            }
        };
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
