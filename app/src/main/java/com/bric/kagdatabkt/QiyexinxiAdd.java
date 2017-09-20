package com.bric.kagdatabkt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;

public class QiyexinxiAdd extends AppCompatActivity {

    private static final int ZIZHI_TAG = 1;
    private static final int RONGYU_TAG = 2;
    private EditText setting_edit_qiye_name;
    private EditText setting_edit_qiye_zizhi;
    private EditText setting_edit_qiye_jianjie;
    private ImageView setting_qiye_yingyezhizhao_add;
    private ImageView setting_qiye_yingyerongyu_add;
    private LinearLayout yingyezhizhao_upload_image_view;
    private LinearLayout qiyerongyu__upload_image_view;
    private ImageView zhizhao_preview_img1;
    private ImageView zhizhao_preview_img2;
    private ImageView zhizhao_preview_img3;
    private ImageView zhizhao_preview_img4;

    private ImageView qiyerongyu_preview_img1;
    private ImageView qiyerongyu_preview_img2;
    private ImageView qiyerongyu_preview_img3;
    private ImageView qiyerongyu_preview_img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiyexinxi_add);
        initview();
        initActionListener();
    }

    private void initview() {
        setting_edit_qiye_name = (EditText) findViewById(R.id.setting_edit_qiye_name);
        setting_edit_qiye_zizhi = (EditText) findViewById(R.id.setting_edit_qiye_zizhi);
        setting_edit_qiye_jianjie = (EditText) findViewById(R.id.setting_edit_qiye_jianjie);
        setting_qiye_yingyezhizhao_add = (ImageView) findViewById(R.id.setting_qiye_yingyezhizhao_add);
        setting_qiye_yingyerongyu_add = (ImageView) findViewById(R.id.setting_qiye_yingyerongyu_add);
        yingyezhizhao_upload_image_view = (LinearLayout) findViewById(R.id.yingyezhizhao_upload_image_view);
        qiyerongyu__upload_image_view = (LinearLayout) findViewById(R.id.qiyerongyu__upload_image_view);
        zhizhao_preview_img1 = (ImageView) findViewById(R.id.zhizhao_preview_img1);
        zhizhao_preview_img2 = (ImageView) findViewById(R.id.zhizhao_preview_img2);
        zhizhao_preview_img3 = (ImageView) findViewById(R.id.zhizhao_preview_img3);
        zhizhao_preview_img4 = (ImageView) findViewById(R.id.zhizhao_preview_img4);
        qiyerongyu_preview_img1 = (ImageView) findViewById(R.id.qiyerongyu_preview_img1);
        qiyerongyu_preview_img2 = (ImageView) findViewById(R.id.qiyerongyu_preview_img2);
        qiyerongyu_preview_img3 = (ImageView) findViewById(R.id.qiyerongyu_preview_img3);
        qiyerongyu_preview_img4 = (ImageView) findViewById(R.id.qiyerongyu_preview_img4);
    }

    private void initActionListener() {
        setting_qiye_yingyezhizhao_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(QiyexinxiAdd.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, ZIZHI_TAG);
            }
        });

        setting_qiye_yingyerongyu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(QiyexinxiAdd.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, RONGYU_TAG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
            if (requestCode == ZIZHI_TAG) {
                if (paths.size() > 0)
                    yingyezhizhao_upload_image_view.setVisibility(View.VISIBLE);
                for (int i = 0; i < paths.size(); i++) {
                    if (i == 0) {
                        zhizhao_preview_img1.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 1) {
                        zhizhao_preview_img2.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 2) {
                        zhizhao_preview_img3.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 3) {
                        zhizhao_preview_img4.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                }
            } else {
                if (paths.size() > 0)
                    qiyerongyu__upload_image_view.setVisibility(View.VISIBLE);
                for (int i = 0; i < paths.size(); i++) {
                    if (i == 0) {
                        qiyerongyu_preview_img1.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 1) {
                        qiyerongyu_preview_img2.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 2) {
                        qiyerongyu_preview_img3.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                    if (i == 3) {
                        qiyerongyu_preview_img4.setImageURI(Uri.fromFile(new File(paths.get(i))));
                    }
                }
            }
        }
    }

}
