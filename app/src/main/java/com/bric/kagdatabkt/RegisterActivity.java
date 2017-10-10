package com.bric.kagdatabkt;

import android.app.Dialog;
import android.content.Context;
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

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText phonenumber;
    private EditText verify_code;
    private EditText register_password;
    private Button register_button;

    private TextView login_forgetpassword;
    private TextView login_register;
    private Button button_getqrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();
    }

    private void initView() {
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
                String username = phonenumber.getText().toString();
                String password = register_password.getText().toString();
                String mobile_code = verify_code.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                String city = sharedPreferences.getString(CommonConstField.LOCATION_CITY, "苏州");
                String district = sharedPreferences.getString(CommonConstField.LOCATION_DISTRICT, "相城区");
                RetrofitHelper.ServiceManager.getBaseService().doRegister(username, password, mobile_code, city, district)
                        .subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(
                        new Observer<RegisterResult>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable arg0) {
                                Log.v(TAG, arg0.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(RegisterResult arg0) {
                                Log.v(TAG, "" + arg0.message);
                            }
                        }
                );
            }
        });
    }

    private boolean checkInput(String username, String password, String mobile_code) {
        boolean result = true;
        return result;
    }

    private boolean checkPhoneNumber(String phone) {
        boolean result = true;
        return result;
    }

    private void showDialog() {
        if (checkPhoneNumber(phonenumber.getText().toString())) {
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
            RetrofitHelper.ServiceManager.getBaseService().doGetQrcode(phonenumber.getText().toString(), "").subscribeOn(Schedulers.newThread())
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
                        RetrofitHelper.ServiceManager.getBaseService().doGetQrcode(phonenumber.getText().toString(), "").subscribeOn(Schedulers.newThread())
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
                        RetrofitHelper.ServiceManager.getBaseService().doSendMsg(phonenumber.getText().toString(), qrcode_edit.getText().toString(), "reg")
                                .subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(
                                new Observer<ResultEntry>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable arg0) {
                                        Log.v(TAG, arg0.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onNext(ResultEntry arg0) {
                                        Log.v(TAG, "" + arg0.success);
                                        Log.v(TAG, arg0.message);
                                        if (arg0.success == 0) {
                                            CustomDialog.this.dismiss();
                                        } else {
                                            Toast.makeText(getContext(), arg0.message, Toast.LENGTH_LONG).show();
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
}