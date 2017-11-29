package com.bric.kagdatabkt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.USER_NAME;

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private EditText phonenumber;
    private EditText verify_code;
    private ImageView forgetpassword_1_qrcodeview;
    private Button register_button;
    private ImageView base_nav_back;
    private ImageView base_nav_right;
    private Button button_getqrcode_button;
    private TextView base_toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_1);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        final String account = sharedPreferences.getString(USER_NAME, "");
        phonenumber.setText(account);
        if (!StringUtils.isEmpty(account)) {
            RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGetQrcode(phonenumber.getText().toString(), "forget").subscribeOn(Schedulers.newThread())
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
                            forgetpassword_1_qrcodeview.setVisibility(View.VISIBLE);
                            forgetpassword_1_qrcodeview.setImageBitmap(arg0);
                            button_getqrcode_button.setVisibility(View.GONE);
                        }
                    }
            );
        }
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
        base_toolbar_title.setText("找回密码");
        base_toolbar_title.setCompoundDrawables(null, null, null, null);
        phonenumber = (EditText) findViewById(R.id.forgetpassword_1_phonenumber);
        verify_code = (EditText) findViewById(R.id.forgetpassword_1_verify_code);
        phonenumber.setHint(R.string.hint_phonenumber);
        phonenumber.setBackgroundResource(0);
        verify_code.setHint(R.string.hint_identifying_code);
        verify_code.setBackgroundResource(0);
        button_getqrcode_button = (Button) findViewById(R.id.button_getqrcode_button);
        button_getqrcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = phonenumber.getText().toString();
                if (StringUtils.isEmpty(account)) {
                    showError("填写账号");
                    return;
                }
                RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGetQrcode(phonenumber.getText().toString(), "forget").subscribeOn(Schedulers.newThread())
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
                                forgetpassword_1_qrcodeview.setVisibility(View.VISIBLE);
                                forgetpassword_1_qrcodeview.setImageBitmap(arg0);
                                button_getqrcode_button.setVisibility(View.GONE);
                            }
                        }
                );
            }
        });

        forgetpassword_1_qrcodeview = (ImageView) findViewById(R.id.forgetpassword_1_qrcodeview);
        forgetpassword_1_qrcodeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = phonenumber.getText().toString();
                if (account == null) {
                    showError("请填写账号");
                }
                RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGetQrcode(account, "forget").subscribeOn(Schedulers.newThread())
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
                                forgetpassword_1_qrcodeview.setImageBitmap(arg0);
                            }
                        }
                );
            }
        });
        register_button = (Button) findViewById(R.id.forgetpassword_button);
        register_button.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   String username = phonenumber.getText().toString();
                                                   String verify = verify_code.getText().toString();
                                                   if (StringUtils.isEmpty(username) || StringUtils.isEmpty(verify)) {
                                                       showError("请填写账号或验证码");
                                                       return;
                                                   }
                                                   RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doSendMsg(username, verify, "forget")
                                                           .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
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
                                                                   if (arg0.success == 0) {
                                                                       Intent registerintent = new Intent(ForgetPasswordActivity.this, ForgetPasswordActivity2.class);
                                                                       registerintent.putExtra(USER_NAME, phonenumber.getText().toString());
                                                                       startActivity(registerintent);
                                                                   } else {
                                                                       showError(arg0.message);
                                                                   }
                                                               }
                                                           }
                                                   );
                                               }
                                           }
        );
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
