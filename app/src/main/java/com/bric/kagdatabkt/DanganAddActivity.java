package com.bric.kagdatabkt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.view.CustomDatePicker;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DanganAddActivity extends FragmentActivity {

    private TextView base_toolbar_title;
    private String title;

    private RelativeLayout document_item1;
    private TextView document_item1_label;
    private EditText document_item1_edit;
    private ImageView document_item1_arrow;
    private RelativeLayout document_item2;
    private TextView document_item2_label;
    private EditText document_item2_edit;
    private ImageView document_item2_arrow;
    private Button take_picture;
    private RelativeLayout document_item3;
    private TextView document_item3_label;
    private EditText document_item3_edit;
    private ImageView document_item3_arrow;
    private RelativeLayout document_item4;
    private TextView document_item4_label;
    private EditText document_item4_edit;
    private ImageView document_item4_arrow;
    private RelativeLayout document_item5;
    private TextView document_item5_label;
    private EditText document_item5_edit;
    private ImageView document_item5_arrow;
    //common field
    private TextView document_operator_label;
    private EditText document_operator_edit;
    private TextView document_media_label;
    private EditText document_media_edit;
    private ImageView document_media_add;
    private TextView document_note_label;
    private EditText document_note_edit;
    private LinearLayout upload_image_view;
    private ImageView preview_img1;
    private ImageView preview_img2;
    private ImageView preview_img3;
    private ImageView preview_img4;

    //date picker
    private CustomDatePicker datePicker;
    private String time;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangancontent);
        initView();
        initprop();
        initCommonprop();
        setActionListener();
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
        document_item1_edit.setHint(date);
    }

    private void initView() {
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);

        document_item1 = (RelativeLayout) findViewById(R.id.document_item1);
        document_item1_label = (TextView) findViewById(R.id.document_item1_label);
        document_item1_edit = (EditText) findViewById(R.id.document_item1_edit);
        document_item1_arrow = (ImageView) findViewById(R.id.document_item1_arrow);
        document_item2 = (RelativeLayout) findViewById(R.id.document_item2);
        document_item2_label = (TextView) findViewById(R.id.document_item2_label);
        document_item2_edit = (EditText) findViewById(R.id.document_item2_edit);
        document_item2_arrow = (ImageView) findViewById(R.id.document_item2_arrow);
        take_picture = (Button) findViewById(R.id.take_picture);
        document_item3 = (RelativeLayout) findViewById(R.id.document_item3);
        document_item3_label = (TextView) findViewById(R.id.document_item3_label);
        document_item3_edit = (EditText) findViewById(R.id.document_item3_edit);
        document_item3_arrow = (ImageView) findViewById(R.id.document_item3_arrow);
        document_item4 = (RelativeLayout) findViewById(R.id.document_item4);
        document_item4_label = (TextView) findViewById(R.id.document_item4_label);
        document_item4_edit = (EditText) findViewById(R.id.document_item4_edit);
        document_item4_arrow = (ImageView) findViewById(R.id.document_item4_arrow);
        document_item5 = (RelativeLayout) findViewById(R.id.document_item5);
        document_item5_label = (TextView) findViewById(R.id.document_item5_label);
        document_item5_edit = (EditText) findViewById(R.id.document_item5_edit);
        document_item5_arrow = (ImageView) findViewById(R.id.document_item5_arrow);
        //common field
        document_operator_label = (TextView) findViewById(R.id.document_operator_label);
        document_operator_edit = (EditText) findViewById(R.id.document_operator_edit);
        document_media_label = (TextView) findViewById(R.id.document_media_label);
        document_media_edit = (EditText) findViewById(R.id.document_media_edit);
        document_media_add = (ImageView) findViewById(R.id.document_media_add);
        document_note_label = (TextView) findViewById(R.id.document_note_label);
        document_note_edit = (EditText) findViewById(R.id.document_note_edit);
        upload_image_view = (LinearLayout) findViewById(R.id.upload_image_view);
        preview_img1 = (ImageView) findViewById(R.id.preview_img1);
        preview_img2 = (ImageView) findViewById(R.id.preview_img2);
        preview_img3 = (ImageView) findViewById(R.id.preview_img3);
        preview_img4 = (ImageView) findViewById(R.id.preview_img4);
    }

    private void initprop() {
        int typekey = getIntent().getIntExtra(CommonConstField.DANGAN_CONTENT_TYPE_KEY, 1);
        switch (typekey) {
            case CommonConstField.DANGAN_CONTENT_TYPE_XIAODU:
                title = getResources().getString(R.string.title_chitang_xiaodu);
                setXiaoduprop();
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_TOUMIAO:
                title = getResources().getString(R.string.title_chitang_toumiao);
                setToumiaoprop();
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_WEISHI:
                title = getResources().getString(R.string.title_chitang_weishi);
                setWeishiprop();
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_BURAO:
                title = getResources().getString(R.string.title_chitang_bulao);
                setBulaoprop();
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_JIANCE:
                title = getResources().getString(R.string.title_chitang_jiance);
                setJianceprop();
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_FANGSHUI:
                title = getResources().getString(R.string.title_chitang_fangshui);
                setFangshuiprop();
                break;
        }
        document_item1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPicker();
            }
        });
        String name = gettypeNameBykey(typekey);
        base_toolbar_title.setText(name);
    }

    private void initCommonprop() {
        //common field
        document_operator_edit.setBackgroundResource(0);
        document_note_edit.setBackgroundResource(0);
    }

    private void setXiaoduprop() {
        document_item1_label.setText(R.string.label_chitang_xiaodudate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_xiaoduchi);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_xiaoduchi);
        document_item3.setVisibility(View.GONE);
        document_item4_label.setText(R.string.label_chitang_xiaoduyongliang);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_xiaoduyongliang);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setToumiaoprop() {
        document_item1_label.setText(R.string.label_chitang_toumiaodate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_source);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_source);
        document_item2_arrow.setVisibility(View.GONE);
        document_item3_label.setText(R.string.label_chitang_miaozhong_catogery);
        document_item3_edit.setEnabled(false);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_miaozhong_catogery);
        document_item4_label.setText(R.string.label_chitang_miaozhong_amount);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_miaozhong_amount);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setWeishiprop() {
        document_item1_label.setText(R.string.label_chitang_weishidate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_weishichi);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_weishichi);
        document_item3_label.setText(R.string.label_chitang_weishisiliao);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_weishisiliao);
        document_item3_arrow.setVisibility(View.GONE);
        take_picture.setVisibility(View.VISIBLE);
        document_item4_label.setText(R.string.label_chitang_weishigoumaishang);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_weishigoumaishang);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5_label.setText(R.string.label_chitang_weishiamount);
        document_item5_edit.setHint(R.string.hint_chitang_weishiamount);
        document_item5_edit.setBackgroundResource(0);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setBulaoprop() {
        document_item1_label.setText(R.string.label_chitang_pulaodate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_pulaocatogery);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_pulaocatogery);
        document_item3.setVisibility(View.GONE);
        document_item4_label.setText(R.string.label_chitang_pulaoamount);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_pulaoamount);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5.setVisibility(View.GONE);
    }

    private void setJianceprop() {
        document_item1_label.setText(R.string.label_chitang_jiancedate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_jianceid);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_xiaoduchi);
        document_item3_label.setText(R.string.label_chitang_jiancexiangmu);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_jiancexiangmu);
        document_item4_label.setText(R.string.label_chitang_jiancejieguo);
        document_item4_edit.setHint(R.string.hint_chitang_jiancejieguo);
        document_item4_edit.setBackgroundResource(0);
        document_item5_label.setText("检测单位");
        document_item5_edit.setBackgroundResource(0);
        document_item5_edit.setHint("请输入检测单位名称");
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setFangshuiprop() {
        document_item1_label.setText(R.string.label_chitang_fangshuidate);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setText(R.string.label_chitang_fangshuichi);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_fangshuichi);
        document_item3_label.setVisibility(View.GONE);
        document_item3_edit.setVisibility(View.GONE);
        document_item3_arrow.setVisibility(View.GONE);
        document_item4_label.setVisibility(View.GONE);
        document_item4_edit.setVisibility(View.GONE);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setActionListener() {
        document_media_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPickerIntent intent = new PhotoPickerIntent(DanganAddActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为9
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
            if (paths.size() > 0)
                upload_image_view.setVisibility(View.VISIBLE);
            for (int i = 0; i < paths.size(); i++) {
                if (i == 0) {
                    preview_img1.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 1) {
                    preview_img2.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 2) {
                    preview_img3.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
                if (i == 3) {
                    preview_img4.setImageURI(Uri.fromFile(new File(paths.get(i))));
                }
            }
        }
    }

    private String gettypeNameBykey(int key) {
        String name = getResources().getString(R.string.title_chitang_xiaodu);
        switch (key) {
            case CommonConstField.DANGAN_CONTENT_TYPE_XIAODU:
                name = getResources().getString(R.string.title_chitang_xiaodu);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_TOUMIAO:
                name = getResources().getString(R.string.title_chitang_toumiao);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_WEISHI:
                name = getResources().getString(R.string.title_chitang_weishi);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_BURAO:
                name = getResources().getString(R.string.title_chitang_bulao);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_JIANCE:
                name = getResources().getString(R.string.title_chitang_jiance);
                break;
            case CommonConstField.DANGAN_CONTENT_TYPE_FANGSHUI:
                name = getResources().getString(R.string.title_chitang_fangshui);
                break;
        }
        return name;
    }

    private void initPicker() {

        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                document_item1_edit.setText(time.split(" ")[0]);
            }
        }, "2007-01-01 00:00", time, document_item1_edit.getY() + document_item1_edit.getHeight());
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
        datePicker.show(date);
    }
}
