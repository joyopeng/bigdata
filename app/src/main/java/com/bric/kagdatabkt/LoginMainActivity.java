package com.bric.kagdatabkt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bumptech.glide.manager.Lifecycle;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginMainActivity extends AppCompatActivity {

    private static final String TAG = LoginMainActivity.class.getSimpleName();
    private EditText login_name_edit;
    private EditText login_password_edit;
    private TextView login_forgetpassword;
    private TextView login_register;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initView();
    }

    private void initView() {
        login_name_edit = (EditText) findViewById(R.id.login_name_edit);
        login_password_edit = (EditText) findViewById(R.id.login_password_edit);
        login_forgetpassword = (TextView) findViewById(R.id.login_forgetpassword);
        login_register = (TextView) findViewById(R.id.login_register);
        login_name_edit.setBackgroundResource(0);
        login_password_edit.setBackgroundResource(0);
        login_button = (Button) findViewById(R.id.login_button);
        login_register.setOnClickListener(clickListener);
        login_forgetpassword.setOnClickListener(clickListener);
        login_button.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_button: {
                    final String username = login_name_edit.getText().toString();
                    String password = login_password_edit.getText().toString();
                    if (!CommonConstField.isMatchered(username)) {
                        showError("请输入正确的电话号码");
                        return;
                    }
                    if (StringUtils.isEmpty(password) || password.length() < 6) {
                        showError("请输入正确的密码");
                        return;
                    }
                    RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doLogin(username, password)
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
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
                                    if (arg0.data.size() > 0) {
                                        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                                        String access_token = ((RegisterResult.Item) (arg0.data.get(0))).Token.access_token;
                                        String user_id = ((RegisterResult.Item) (arg0.data.get(0))).User.id;
                                        String appkey = ((RegisterResult.Item) (arg0.data.get(0))).User.appkey;
                                        sharedPreferences.edit().putString(CommonConstField.ACCESS_TOKEN, access_token).commit();
                                        sharedPreferences.edit().putString(CommonConstField.USER_ID, user_id).commit();
                                        sharedPreferences.edit().putString(CommonConstField.USER_NAME, username).commit();
                                        sharedPreferences.edit().putString(CommonConstField.APP_KEY, appkey).commit();
                                        Intent registerintent = new Intent(LoginMainActivity.this, MainActivity.class);
                                        startActivity(registerintent);
                                        finish();
                                    } else {
                                        showError(arg0.message);
                                    }
                                }
                            }
                    );
                }
                break;
                case R.id.login_register: {
                    Intent registerintent = new Intent(LoginMainActivity.this, RegisterActivity.class);
                    startActivity(registerintent);
                }
                break;
                case R.id.login_forgetpassword: {
                    Intent registerintent = new Intent(LoginMainActivity.this, ForgetPasswordActivity.class);
                    startActivity(registerintent);
                }
                break;
            }
        }
    };

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
