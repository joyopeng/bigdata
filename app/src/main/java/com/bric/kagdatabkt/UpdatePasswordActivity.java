package com.bric.kagdatabkt;

import android.content.Intent;
import android.content.SharedPreferences;
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

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpdatePasswordActivity extends AppCompatActivity {

    private static final String TAG = UpdatePasswordActivity.class.getSimpleName();
    private EditText origin_password;
    private EditText new_password;
    private EditText repeate_new_password;
    private Button update_password_button;
    private ImageView base_nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);
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
        origin_password = (EditText) findViewById(R.id.origin_password);
        origin_password.setHint("填写原密码");
        origin_password.setBackgroundResource(0);
        new_password = (EditText) findViewById(R.id.new_password);
        new_password.setHint("填写新密码");
        new_password.setBackgroundResource(0);
        repeate_new_password = (EditText) findViewById(R.id.repeate_new_password);
        repeate_new_password.setHint("填写新密码再次确认");
        repeate_new_password.setBackgroundResource(0);

        update_password_button = (Button) findViewById(R.id.update_password_button);
        update_password_button.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                                                          String access_token = sharedPreferences.getString(CommonConstField.ACCESS_TOKEN, "");
                                                          String user_id = sharedPreferences.getString(CommonConstField.USER_ID, "");
                                                          String oritin_pw = origin_password.getText().toString();
                                                          String new_pw = new_password.getText().toString();
                                                          String repeate_new_pw = repeate_new_password.getText().toString();
                                                          RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doUpdatePassword(user_id, access_token, oritin_pw, new_pw, repeate_new_pw)
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
                                                                              UpdatePasswordActivity.this.finish();
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
