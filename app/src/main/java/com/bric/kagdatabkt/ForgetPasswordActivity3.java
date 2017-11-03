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

import com.bric.kagdatabkt.entry.RegisterResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;

import rx.Observer;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.USER_NAME;

public class ForgetPasswordActivity3 extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity3.class.getSimpleName();
    private EditText password;
    private Button forgetpassword_3_button;
    private ImageView base_nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_3);
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
        password = (EditText) findViewById(R.id.forgetpassword_3_newpassword);
        password.setHint(R.string.hint_new_phonenumber);
        password.setBackgroundResource(0);
        final String account = getIntent().getStringExtra(USER_NAME);
        forgetpassword_3_button = (Button) findViewById(R.id.forgetpassword_3_button);
        forgetpassword_3_button.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View view) {
                                                           String pw = password.getText().toString();
                                                           RetrofitHelper.ServiceManager.getBaseService().doForgetPassword_2(account, pw, pw)
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
                                                                           if (arg0.success == 0) {
                                                                               Intent registerintent = new Intent(ForgetPasswordActivity3.this, MainActivity.class);
                                                                               startActivity(registerintent);
                                                                           }
                                                                       }
                                                                   }
                                                           );
                                                       }
                                                   }
        );
    }

}
