package com.bric.kagdatabkt;

import android.content.Intent;
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

import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ForgetPasswordActivity2 extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity2.class.getSimpleName();
    private TextView forgetpassword_2_title;
    private EditText verify_code;
    private Button forgetpassword_button_send_msg;
    private Button forgetpassword2_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_2);
        initView();
    }

    private void initView() {
        forgetpassword_2_title = (TextView) findViewById(R.id.forgetpassword_2_title);
        verify_code = (EditText) findViewById(R.id.forgetpassword_1_verify_code);
        verify_code.setHint(R.string.hint_identifying_code);
        verify_code.setBackgroundResource(0);
        forgetpassword_button_send_msg = (Button) findViewById(R.id.forgetpassword_button_send_msg);
        forgetpassword2_button = (Button) findViewById(R.id.forgetpassword2_button);
        forgetpassword_button_send_msg.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {
                                                                  String verify = verify_code.getText().toString();
                                                                  RetrofitHelper.ServiceManager.getBaseService().doForgetPassword_1("15221584146", verify)
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

                                                                                  }
                                                                              }
                                                                          }
                                                                  );
                                                              }
                                                          }
        );

        forgetpassword2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(ForgetPasswordActivity2.this, ForgetPasswordActivity3.class);
                startActivity(registerintent);
            }
        });
    }

}
