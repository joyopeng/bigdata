package com.bric.kagdatabkt;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bric.kagdatabkt.entry.DanganDetailResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.ACCESS_TOKEN;

public class DanganDetailActivity extends FragmentActivity {
    private static final String TAG = DanganDetailActivity.class.getSimpleName();
    private TextView base_toolbar_title;
    private ImageView base_nav_back;
    private HashMap<Integer, String> dailytype = new HashMap();
    private TextView detail_item1_label;
    private TextView detail_item1_value;
    private LinearLayout detail_item2;
    private TextView detail_item2_label;
    private TextView detail_item2_value;
    private LinearLayout detail_item3;
    private TextView detail_item3_label;
    private TextView detail_item3_value;
    private LinearLayout detail_item4;
    private TextView detail_item4_label;
    private TextView detail_item4_value;
    private LinearLayout detail_item5;
    private TextView detail_item5_label;
    private TextView detail_item5_value;
    private LinearLayout detail_item6;
    private TextView detail_item6_label;
    private TextView detail_item6_value;
    private TextView detail_item_note_label;
    private TextView detail_item_note_value;

    private LinearLayout image_review_view;
    private ImageView preview_img1;
    private ImageView preview_img2;
    private ImageView preview_img3;
    private ImageView preview_img4;

    private String title;
    private String filebag_numid;
    private int job_type_id;
    private int jobid;
    private String access_token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangan_detail);
        filebag_numid = getIntent().getStringExtra(CommonConstField.NUMID_KEY);
        job_type_id = Integer.parseInt(getIntent().getStringExtra(CommonConstField.JOB_TYPE_ID_KEY));
        jobid = Integer.parseInt(getIntent().getStringExtra(CommonConstField.JOB_ID));
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        initView();
        gettypeNameBykey(job_type_id);
        fetchDanganData();
    }

    private void initView() {
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detail_item1_label = (TextView) findViewById(R.id.detail_item1_label);
        detail_item1_value = (TextView) findViewById(R.id.detail_item1_value);
        detail_item2_label = (TextView) findViewById(R.id.detail_item2_label);
        detail_item2_value = (TextView) findViewById(R.id.detail_item2_value);
        detail_item3_label = (TextView) findViewById(R.id.detail_item3_label);
        detail_item3_value = (TextView) findViewById(R.id.detail_item3_value);
        detail_item4_label = (TextView) findViewById(R.id.detail_item4_label);
        detail_item4_value = (TextView) findViewById(R.id.detail_item4_value);
        detail_item5_label = (TextView) findViewById(R.id.detail_item5_label);
        detail_item5_value = (TextView) findViewById(R.id.detail_item5_value);
        detail_item6 = (LinearLayout) findViewById(R.id.detail_item6);
        detail_item6_label = (TextView) findViewById(R.id.detail_item6_label);
        detail_item6_value = (TextView) findViewById(R.id.detail_item6_value);
        detail_item_note_label = (TextView) findViewById(R.id.detail_item_note_label);
        detail_item_note_value = (TextView) findViewById(R.id.detail_item_note_value);
        image_review_view = (LinearLayout) findViewById(R.id.image_review_view);
        preview_img1 = (ImageView) findViewById(R.id.preview_img1);
        preview_img2 = (ImageView) findViewById(R.id.preview_img2);
        preview_img3 = (ImageView) findViewById(R.id.preview_img3);
        preview_img4 = (ImageView) findViewById(R.id.preview_img4);
    }

    private void gettypeNameBykey(int key) {
        String name = getResources().getString(R.string.title_chitang_xiaodu);
        switch (key) {
            case CommonConstField.DANGAN_CONTENT_TYPE_XIAODU:
                name = getResources().getString(R.string.title_chitang_xiaodu);
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("防治日期:");
                detail_item3_label.setText("防治用品:");
                detail_item4_label.setText("用量:");
                detail_item5_label.setText("操作人:");
                detail_item6.setVisibility(View.GONE);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_TOUMIAO:
                name = getResources().getString(R.string.title_chitang_toumiao);
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("投苗日期:");
                detail_item3_label.setText("投苗来源:");
                detail_item4_label.setText("投苗种类:");
                detail_item5_label.setText("投苗量:");
                detail_item6_label.setText("操作人:");
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_WEISHI:
                name = getResources().getString(R.string.title_chitang_weishi);
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("喂食日期:");
                detail_item3_label.setText("饲料名称:");
                detail_item4_label.setText("购买商:");
                detail_item5_label.setText("投苗量:");
                detail_item6_label.setText("操作人:");
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_BURAO:
                name = getResources().getString(R.string.title_chitang_bulao);
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("捕捞日期:");
                detail_item3_label.setText("捕捞种类:");
                detail_item4_label.setText("重量:");
                detail_item5_label.setText("操作人:");
                detail_item6.setVisibility(View.GONE);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_JIANCE:
                name = getResources().getString(R.string.title_chitang_jiance);
                dailytype.put(1, "合格");
                dailytype.put(0, "不合格");
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("检测日期:");
                detail_item3_label.setText("检测品种:");
                detail_item4_label.setText("检测项目:");
                detail_item5_label.setText("检测结果:");
                detail_item6_label.setText("检测单位:");
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_FANGSHUI:
                name = getResources().getString(R.string.title_chitang_fangshui);
                dailytype.put(1, "种草");
                dailytype.put(2, "施肥");
                dailytype.put(2, "调水");
                detail_item1_label.setText("档案名称:");
                detail_item2_label.setText("管理日期:");
                detail_item3_label.setText("管理类型:");
                detail_item4_label.setText("投入量:");
                detail_item5_label.setText("操作人:");
                detail_item6.setVisibility(View.GONE);
                break;
        }
        base_toolbar_title.setText(name);
    }

    private void fetchDanganData() {
        RetrofitHelper.ServiceManager.getBaseService().doGet_job_info(access_token, filebag_numid, job_type_id, jobid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<DanganDetailResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        Log.v(TAG, arg0.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(DanganDetailResult arg0) {
                        if (arg0.success == 0) {
                            fillData(arg0.data.get(0));
                        }
                    }
                }
        );
    }

    private void fillData(DanganDetailResult.Item item) {
        String productName = null;
        DanganDetailResult.Job job = item.Job;
        ArrayList<String> aqjobs = item.AqJobReport;
        if (aqjobs != null && aqjobs.size() > 0) {
            image_review_view.setVisibility(View.VISIBLE);
            for (int i = 0; i < aqjobs.size(); i++) {
                if (i == 0) {
                    Picasso.with(this).load(aqjobs.get(i)).into(preview_img1);
                }
                if (i == 1) {
                    Picasso.with(this).load(aqjobs.get(i)).into(preview_img2);
                }
                if (i == 2) {
                    Picasso.with(this).load(aqjobs.get(i)).into(preview_img3);
                }
                if (i == 3) {
                    Picasso.with(this).load(aqjobs.get(i)).into(preview_img4);
                }
            }
        }
        if (job_type_id == 2 || job_type_id == 4 || job_type_id == 5) {
            productName = item.AqBreedProduct.name;
        }
        detail_item_note_value.setText(job.remark);
        switch (job_type_id) {
            case 1: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(job.supplies);
                detail_item4_value.setText(job.consumption);
                detail_item5_value.setText(job.operator);
            }
            break;
            case 2: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(job.source);
                detail_item4_value.setText(productName);
                detail_item5_value.setText(job.consumption);
                detail_item6_value.setText(job.operator);
            }
            break;
            case 3: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(job.feed_name);
                detail_item4_value.setText(job.buyer);
                detail_item5_value.setText(job.consumption);
                detail_item6_value.setText(job.operator);
            }
            break;
            case 4: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(productName);
                detail_item4_value.setText(job.consumption);
                detail_item5_value.setText(job.operator);
            }
            break;
            case 5: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(productName);
                detail_item4_value.setText(job.project_name);
                detail_item5_value.setText(dailytype.get(Integer.parseInt(job.result)));
                detail_item6_value.setText(job.operator);
            }
            break;
            case 6: {
                detail_item1_value.setText(job.title);
                detail_item2_value.setText(job.control_date);
                detail_item3_value.setText(dailytype.get(Integer.parseInt(job.daily_type_id)));
                detail_item4_value.setText(job.consumption);
                detail_item5_value.setText(job.operator);
            }
            break;
        }
    }
}
