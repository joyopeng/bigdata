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

public class ForgetPasswordActivity extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private EditText phonenumber;
    private EditText verify_code;
    private ImageView forgetpassword_1_qrcodeview;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_1);
        initView();
        RetrofitHelper.ServiceManager.getBaseService().doGetQrcode("15221584146", "forget").subscribeOn(Schedulers.newThread())
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

    private void initView() {
        phonenumber = (EditText) findViewById(R.id.forgetpassword_1_phonenumber);
        verify_code = (EditText) findViewById(R.id.forgetpassword_1_verify_code);
        phonenumber.setHint(R.string.hint_phonenumber);
        phonenumber.setBackgroundResource(0);
        verify_code.setHint(R.string.hint_identifying_code);
        verify_code.setBackgroundResource(0);
        forgetpassword_1_qrcodeview = (ImageView) findViewById(R.id.forgetpassword_1_qrcodeview);
        forgetpassword_1_qrcodeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitHelper.ServiceManager.getBaseService().doGetQrcode("15221584146", "forget").subscribeOn(Schedulers.newThread())
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
                                                   RetrofitHelper.ServiceManager.getBaseService().doSendMsg(username, verify, "forget")
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
                                                                       Intent registerintent = new Intent(ForgetPasswordActivity.this, ForgetPasswordActivity2.class);
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
