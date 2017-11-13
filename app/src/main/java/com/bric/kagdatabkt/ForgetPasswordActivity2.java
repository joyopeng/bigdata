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

public class ForgetPasswordActivity2 extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity2.class.getSimpleName();
    private TextView forgetpassword_2_title;
    private EditText verify_code;
    private Button forgetpassword_button_send_msg;
    private Button forgetpassword2_button;
    private ImageView base_nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_2);
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
        forgetpassword_2_title = (TextView) findViewById(R.id.forgetpassword_2_title);
        verify_code = (EditText) findViewById(R.id.forgetpassword_1_verify_code);
        verify_code.setHint(R.string.hint_identifying_code);
        verify_code.setBackgroundResource(0);
//        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
//        final String account = sharedPreferences.getString(USER_NAME, "");
        final String account = getIntent().getStringExtra(USER_NAME);
        forgetpassword_2_title.setText("请输入手机号" + transforPhoneNumber(account) + "收到的验证码");
        forgetpassword_button_send_msg = (Button) findViewById(R.id.forgetpassword_button_send_msg);
        forgetpassword2_button = (Button) findViewById(R.id.forgetpassword2_button);
        forgetpassword2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verify = verify_code.getText().toString();
                RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doForgetPassword_1(account, verify)
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
                                    Intent registerintent = new Intent(ForgetPasswordActivity2.this, ForgetPasswordActivity3.class);
                                    registerintent.putExtra(USER_NAME, account);
                                    startActivity(registerintent);
                                } else {
                                    showError(arg0.message);
                                }
                            }
                        }
                );

            }
        });
    }


    private String transforPhoneNumber(String number) {
        number = number.substring(0, 3) + "XXXX" + number.substring(7, number.length());
        return number;
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}