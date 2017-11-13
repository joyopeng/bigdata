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

import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.entry.QrcodeInfoResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.ACCESS_TOKEN;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_FISHING_ID;
import static com.bric.kagdatabkt.utils.CommonConstField.NUMID_KEY;

public class QrcodeApplyActivity extends AppCompatActivity {

    private static final String TAG = QrcodeApplyActivity.class.getSimpleName();
    private ImageView base_nav_back;

    private EditText qrcode_numid;
    private EditText qrcode_product_name;
    private EditText qrcode_apply_count;
    private Button qrcode_apply_button;

    private int job_fishing_id;
    private String access_token;
    private String numid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_apply);
        job_fishing_id = Integer.parseInt(getIntent().getStringExtra(JOB_FISHING_ID));
        numid = getIntent().getStringExtra(NUMID_KEY);
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        initView();
        fetchData();
    }

    private void initView() {
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qrcode_numid = (EditText) findViewById(R.id.qrcode_numid);
        qrcode_product_name = (EditText) findViewById(R.id.qrcode_product_name);
        qrcode_apply_count = (EditText) findViewById(R.id.qrcode_apply_count);
        qrcode_apply_button = (Button) findViewById(R.id.qrcode_apply_button);
        qrcode_numid.setHint("填写关联码");
        qrcode_numid.setBackgroundResource(0);
        qrcode_numid.setText(numid);
        qrcode_numid.setEnabled(false);
        qrcode_product_name.setHint("填写产品名称");
        qrcode_product_name.setBackgroundResource(0);
        qrcode_apply_count.setHint("填写申请数量");
        qrcode_apply_count.setBackgroundResource(0);
        qrcode_apply_button.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       if (StringUtils.isEmpty(qrcode_apply_count.getText().toString())) {
                                                           showError("填写申请数量");
                                                           return;
                                                       }
                                                       int count = Integer.parseInt(qrcode_apply_count.getText().toString());
                                                       RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doApply_qrcode(access_token, job_fishing_id, count)
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
                                                                           finish();
                                                                       }
                                                                   }
                                                               }
                                                       );
                                                   }
                                               }
        );
    }

    private void fetchData() {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGet_apply_qrcode_list(access_token, job_fishing_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<QrcodeInfoResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        Log.v(TAG, arg0.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(QrcodeInfoResult arg0) {
                        if (arg0.success == 0) {
                            QrcodeInfoResult.Item item = arg0.data.get(0);
                            String name = item.AqBreedProduct.name;
                            qrcode_product_name.setText(name);
                        }
                    }
                }
        );
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
