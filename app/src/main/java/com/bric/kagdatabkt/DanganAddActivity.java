package com.bric.kagdatabkt;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.entry.ImageResult;
import com.bric.kagdatabkt.entry.ProductResult;
import com.bric.kagdatabkt.entry.ResultEntry;
import com.bric.kagdatabkt.entry.WeishiImageResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.view.layout.LableTextView;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.ACCESS_TOKEN;

public class DanganAddActivity extends FragmentActivity {
    private static final String TAG = DanganAddActivity.class.getSimpleName();
    private static final int TAKEPICTURE_ACTION_TYPE = 1;
    private static final int SELECTPICTURE_ACTION_TYPE = 2;
    private TextView base_toolbar_title;
    private String title;

    private ImageView base_nav_back;
    private ImageView base_nav_right;
    private RelativeLayout document_item0;
    private LableTextView document_item0_label;
    private EditText document_item0_edit;
    private RelativeLayout document_item1;
    private LableTextView document_item1_label;
    private EditText document_item1_edit;
    private ImageView document_item1_arrow;
    private RelativeLayout document_item2;
    private LableTextView document_item2_label;
    private EditText document_item2_edit;
    private ImageView document_item2_arrow;
    private ImageView take_picture;
    private RelativeLayout document_item3;
    private LableTextView document_item3_label;
    private EditText document_item3_edit;
    private ImageView document_item3_arrow;
    private RelativeLayout document_item4;
    private LableTextView document_item4_label;
    private EditText document_item4_edit;
    private ImageView document_item4_arrow;
    private RelativeLayout document_item5;
    private LableTextView document_item5_label;
    private EditText document_item5_edit;
    private ImageView document_item5_arrow;
    //common field
    private LableTextView document_operator_label;
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
    private Button adddocument;
    //date picker
    private TimePickerView pvTime;
    private String time;
    private String date;
    ArrayList<ProductResult.SubItem> choseItems;
    private OptionsPickerView choseview;

    private String filebag_numid;
    private int job_type_id;
    private ArrayList<String> imagepath;
    private String access_token;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangancontent);
        filebag_numid = getIntent().getStringExtra(CommonConstField.NUMID_KEY);
        job_type_id = getIntent().getIntExtra(CommonConstField.JOB_TYPE_ID_KEY, 0);
        if (2 == job_type_id || 4 == job_type_id || 5 == job_type_id) {
            String temp_numid = filebag_numid;
            if (2 == job_type_id)
                temp_numid = "";
            fillChoseData(temp_numid);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        initView();
        initprop();
        initCommonprop();
        setActionListener();
        //
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
        document_item1_edit.setText(date);
    }

    private void initView() {
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setCompoundDrawables(null, null, null, null);
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        base_nav_right = (ImageView) findViewById(R.id.base_nav_right);
        base_nav_right.setVisibility(View.GONE);
        document_item0_label = (LableTextView) findViewById(R.id.document_item0_label);
        document_item0_edit = (EditText) findViewById(R.id.document_item0_edit);
        document_item1 = (RelativeLayout) findViewById(R.id.document_item1);
        document_item1_label = (LableTextView) findViewById(R.id.document_item1_label);
        document_item1_edit = (EditText) findViewById(R.id.document_item1_edit);
        document_item1_arrow = (ImageView) findViewById(R.id.document_item1_arrow);
        document_item2 = (RelativeLayout) findViewById(R.id.document_item2);
        document_item2_label = (LableTextView) findViewById(R.id.document_item2_label);
        document_item2_edit = (EditText) findViewById(R.id.document_item2_edit);
        document_item2_arrow = (ImageView) findViewById(R.id.document_item2_arrow);
        document_item3 = (RelativeLayout) findViewById(R.id.document_item3);
        take_picture = (ImageView) findViewById(R.id.take_picture);
        document_item3_label = (LableTextView) findViewById(R.id.document_item3_label);
        document_item3_edit = (EditText) findViewById(R.id.document_item3_edit);
        document_item3_arrow = (ImageView) findViewById(R.id.document_item3_arrow);
        document_item4 = (RelativeLayout) findViewById(R.id.document_item4);
        document_item4_label = (LableTextView) findViewById(R.id.document_item4_label);
        document_item4_edit = (EditText) findViewById(R.id.document_item4_edit);
        document_item4_arrow = (ImageView) findViewById(R.id.document_item4_arrow);
        document_item5 = (RelativeLayout) findViewById(R.id.document_item5);
        document_item5_label = (LableTextView) findViewById(R.id.document_item5_label);
        document_item5_edit = (EditText) findViewById(R.id.document_item5_edit);
        document_item5_arrow = (ImageView) findViewById(R.id.document_item5_arrow);
        //common field
        document_operator_label = (LableTextView) findViewById(R.id.document_operator_label);
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
        adddocument = (Button) findViewById(R.id.adddocument);
    }

    private void initprop() {
        int typekey = getIntent().getIntExtra(CommonConstField.JOB_TYPE_ID_KEY, 1);
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
        initPicker();
        document_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show(document_item1_edit, true);
            }
        });

        document_item1_edit.setFocusable(false);
        document_item1_edit.setSelected(false);
        document_item1_edit.setBackgroundResource(0);
        document_item1_edit.setClickable(true);
        document_item1_edit.setCursorVisible(false);
        document_item1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item1.performClick();
            }
        });

        String name = gettypeNameBykey(typekey);
        base_toolbar_title.setText(name);
    }

    private void initCommonprop() {
        //common field
        document_item0_label.setText("档案名称");
        document_item0_edit.setHint("请输入档案名称");
        document_item0_edit.setBackgroundResource(0);
        document_operator_label.setText("操作人");
        document_operator_edit.setBackgroundResource(0);
        document_note_edit.setBackgroundResource(0);
    }

    private void setXiaoduprop() {
        document_item1_label.setCusText(R.string.label_chitang_xiaodudate);
        document_item1_edit.setKeyListener(null);
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setCusText(R.string.label_chitang_xiaoduchi);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_xiaoduchi);
        document_item3.setVisibility(View.GONE);
        document_item3_label.setCusText(R.string.label_chitang_xiaodushiji);
        document_item3_edit.setEnabled(false);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_xiaodushiji);
        document_item4_label.setCusText(R.string.label_chitang_xiaoduyongliang);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_xiaoduyongliang);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setToumiaoprop() {
        document_item1_label.setCusText(R.string.label_chitang_toumiaodate);
        document_item1_edit.setKeyListener(null);
//        document_item1_edit.setHint("2017.07.13");
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setCusText(R.string.label_chitang_source);
//        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_source);
        document_item2_arrow.setVisibility(View.GONE);
        document_item3_label.setCusText(R.string.label_chitang_miaozhong_catogery);
//        document_item3_edit.setEnabled(false);
        document_item3_edit.setFocusable(false);
        document_item3_edit.setSelected(false);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_miaozhong_catogery);
        document_item3_edit.setClickable(true);
        document_item3_edit.setCursorVisible(false);
        document_item3_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item3.performClick();
            }
        });
        document_item3.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        document_item3_edit.setTag("-1");
        document_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choseItems != null && choseItems.size() > 0) {
                    initCustomOptionPicker(choseItems, document_item3_edit);
                    if (choseview != null)
                        choseview.show(true);
                }
            }
        });
        document_item4_label.setCusText(R.string.label_chitang_miaozhong_amount);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_miaozhong_amount);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setWeishiprop() {
        document_item1_label.setCusText(R.string.label_chitang_weishidate);
        document_item1_edit.setKeyListener(null);
//        document_item1_edit.setHint("2017.07.13");
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setCusText(R.string.label_chitang_weishichi);
//        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_weishichi);
        take_picture.setVisibility(View.VISIBLE);

        document_item3.setVisibility(View.GONE);
        document_item3_label.setCusText(R.string.label_chitang_weishisiliao);
//        document_item3_edit.setEnabled(false);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_weishisiliao);
        document_item3_arrow.setVisibility(View.GONE);
        document_item4_label.setCusText(R.string.label_chitang_weishigoumaishang);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_weishigoumaishang);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5_label.setCusText(R.string.label_chitang_weishiamount);
        document_item5_edit.setHint(R.string.hint_chitang_weishiamount);
        document_item5_edit.setBackgroundResource(0);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setBulaoprop() {
        document_item1_label.setCusText(R.string.label_chitang_pulaodate);
        document_item1_edit.setKeyListener(null);
//        document_item1_edit.setHint("2017.07.13");
        document_item1_edit.setBackgroundResource(0);
        document_item2.setVisibility(View.GONE);
        document_item2_label.setCusText(R.string.label_chitang_pulaochi);
        document_item2_edit.setEnabled(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_pulaochi);
        document_item3_label.setCusText(R.string.label_chitang_pulaocatogery);
        document_item3_edit.setFocusable(false);
        document_item3_edit.setSelected(false);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_miaozhong_catogery);
        document_item3_edit.setClickable(true);
        document_item3_edit.setCursorVisible(false);
        document_item3_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item3.performClick();
            }
        });
        document_item3_edit.setHint(R.string.hint_chitang_pulaocatogery);
        document_item3_edit.setTag("-1");
        document_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choseItems != null && choseItems.size() > 0) {
                    initCustomOptionPicker(choseItems, document_item3_edit);
                    if (choseview != null)
                        choseview.show(true);
                }
            }
        });
        document_item4_label.setCusText(R.string.label_chitang_pulaoamount);
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setHint(R.string.hint_chitang_pulaoamount);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setJianceprop() {
        document_item1_label.setCusText(R.string.label_chitang_jiancedate);
        document_item1_edit.setKeyListener(null);
//        document_item1_edit.setHint("2017.07.13");
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setCusText(R.string.label_chitang_jianceid);
        document_item2_edit.setFocusable(false);
        document_item2_edit.setSelected(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_miaozhong_catogery);
        document_item2_edit.setClickable(true);
        document_item2_edit.setCursorVisible(false);
        document_item2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item2.performClick();
            }
        });
        document_item2_edit.setHint("请选择检测品种");
        document_item2_edit.setTag("-1");
        document_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choseItems != null && choseItems.size() > 0) {
                    initCustomOptionPicker(choseItems, document_item2_edit);
                    if (choseview != null)
                        choseview.show(true);
                }
            }
        });
        document_item3_label.setCusText(R.string.label_chitang_jiancexiangmu);
//        document_item3_edit.setEnabled(false);
        document_item3_edit.setHint(R.string.hint_chitang_jiancexiangmu);
        document_item3_edit.setBackgroundResource(0);
        document_item4_label.setCusText(R.string.label_chitang_jiancejieguo);
        document_item4_edit.setHint(R.string.hint_chitang_jiancejieguo);
        document_item4_edit.setTag("-1");
        document_item4_edit.setBackgroundResource(0);
        document_item4_edit.setFocusable(false);
        document_item4_edit.setSelected(false);
        document_item4_edit.setClickable(true);
        document_item4_edit.setCursorVisible(false);
        document_item4_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item4.performClick();
            }
        });
        document_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProductResult.SubItem> dd = new ArrayList<ProductResult.SubItem>();
                ProductResult result1 = new ProductResult();
                ProductResult.SubItem hege = result1.new SubItem();
                hege.id = "1";
                hege.name = "合格";
                ProductResult result2 = new ProductResult();
                ProductResult.SubItem buhege = result2.new SubItem();
                buhege.id = "0";
                buhege.name = "不合格";
                dd.add(hege);
                dd.add(buhege);
                initCustomOptionPicker(dd, document_item4_edit);
                if (choseview != null)
                    choseview.show(true);
            }
        });
        document_item4_arrow.setVisibility(View.GONE);
        document_operator_label.setText("检测单位");
        document_operator_edit.setHint("请输入检测单位名称");
        document_item5.setVisibility(View.GONE);
        document_item5_label.setVisibility(View.GONE);
        document_item5_edit.setVisibility(View.GONE);
        document_item5_arrow.setVisibility(View.GONE);
    }

    private void setFangshuiprop() {
        document_item1_label.setCusText(R.string.label_chitang_fangshuidate);
        document_item1_edit.setKeyListener(null);
//        document_item1_edit.setHint("2017.07.13");
        document_item1_edit.setBackgroundResource(0);
        document_item2_label.setCusText(R.string.label_chitang_fangshuichi);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setHint(R.string.hint_chitang_fangshuichi);
        document_item2_edit.setFocusable(false);
        document_item2_edit.setSelected(false);
        document_item2_edit.setBackgroundResource(0);
        document_item2_edit.setClickable(true);
        document_item2_edit.setCursorVisible(false);
        document_item2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                document_item2.performClick();
            }
        });
        document_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProductResult.SubItem> dd = new ArrayList<ProductResult.SubItem>();
                ProductResult result1 = new ProductResult();
                ProductResult.SubItem s1 = result1.new SubItem();
                s1.id = "1";
                s1.name = "种草";
                ProductResult result2 = new ProductResult();
                ProductResult.SubItem s2 = result2.new SubItem();
                s2.id = "2";
                s2.name = "施肥";
                ProductResult result3 = new ProductResult();
                ProductResult.SubItem s3 = result3.new SubItem();
                s3.id = "3";
                s3.name = "调水";
                dd.add(s1);
                dd.add(s2);
                dd.add(s3);
                initCustomOptionPicker(dd, document_item2_edit);
                if (choseview != null)
                    choseview.show(true);
            }
        });
        document_item3_label.setCusText(R.string.label_chitang_guanli_touruliang);
        document_item3_edit.setBackgroundResource(0);
        document_item3_edit.setHint(R.string.hint_chitang_guanli_touruliang);
        document_item3_arrow.setVisibility(View.GONE);
        document_item4.setVisibility(View.GONE);
        document_item4_label.setVisibility(View.GONE);
        document_item4_edit.setVisibility(View.GONE);
//        document_item4_edit.setHint(R.string.hint_chitang_xiaoduyongliang);
        document_item4_arrow.setVisibility(View.GONE);
        document_item5.setVisibility(View.GONE);
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
                startActivityForResult(intent, SELECTPICTURE_ACTION_TYPE);
            }
        });

        adddocument.setOnClickListener(sumitButtonClickListener);
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    showPreviewDialog();
                } else {
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File file = new File(Environment.getExternalStorageDirectory() +
                                File.separator + Environment.DIRECTORY_DCIM + File.separator);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        String fileName = getPhotoFileName() + ".jpg";
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +
                                File.separator + Environment.DIRECTORY_DCIM + File.separator + fileName));
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, TAKEPICTURE_ACTION_TYPE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (SELECTPICTURE_ACTION_TYPE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<String> paths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                imagepath = paths;
                if (paths.size() > 0)
                    upload_image_view.setVisibility(View.VISIBLE);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ALPHA_8;
                options.inSampleSize = 8;
                for (int i = 0; i < paths.size(); i++) {
//                    paths.set(i, getTruePath(paths.get(i)));
                    Log.v(TAG, "paths url =" + paths.get(i));
                    if (i == 0) {
//                        Picasso.with(getBaseContext()).load(new File(paths.get(i))).into(preview_img1);
                        preview_img1.setImageBitmap(BitmapFactory.decodeFile(paths.get(i), options));
                    }
                    if (i == 1) {
//                        Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img2);
                        preview_img2.setImageBitmap(BitmapFactory.decodeFile(paths.get(i), options));
                    }
                    if (i == 2) {
//                        Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img3);
                        preview_img3.setImageBitmap(BitmapFactory.decodeFile(paths.get(i), options));
                    }
                    if (i == 3) {
//                        Picasso.with(getBaseContext()).load(new File(paths.get(i))).fit().config(Bitmap.Config.RGB_565).into(preview_img4);
                        preview_img4.setImageBitmap(BitmapFactory.decodeFile(paths.get(i), options));
                    }
                }
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = null;
                if (data != null && data.getData() != null) {
                    uri = data.getData();
                    Log.v(TAG, "uri = " + uri);
                } else {
                    uri = photoUri;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ALPHA_8;
                options.inSampleSize = 10;
//                Picasso.with(getBaseContext()).load(uri).resize(50, 50).centerCrop().into(take_picture);
                take_picture.setBackground(Drawable.createFromPath(uri.getEncodedPath()));
                take_picture.setTag(photoUri);
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
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                EditText btn = (EditText) v;
                btn.setText(getTime(date));
            }
        }).isDialog(false).setTitleText("请选择日期")
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void initCustomOptionPicker(final ArrayList<ProductResult.SubItem> datas, final EditText hostview) {
        choseview = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = cardItem.get(options1).getPickerViewText();
                hostview.setTag(datas.get(options1).id);
                hostview.setText(datas.get(options1).name);
                Log.v(TAG, "hostview tag = " + hostview.getTag());
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView select = (TextView) v.findViewById(R.id.chose_select);
                TextView ivCancel = (TextView) v.findViewById(R.id.chose_cancel);
                select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseview.returnData();
                        choseview.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseview.dismiss();
                    }
                });
            }
        }).isDialog(false).build();

        choseview.setPicker(datas);//添加数据
    }

    private void fillChoseData(String numid) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGet_breed_products(access_token, numid, String.valueOf(2))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<ProductResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                    }

                    @Override
                    public void onNext(ProductResult arg0) {
                        if (arg0.success == 0) {
                            choseItems = arg0.data.get(0).List;
//                            initCustomOptionPicker(choseItems, document_item3_edit);
                        }
                    }
                }
        );
    }

    private View.OnClickListener sumitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (job_type_id) {
                case 1: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final String yongpin = document_item2_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    final String consumption = document_item4_edit.getText().toString();
                    final String note = document_note_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(date) || StringUtils.isEmpty(yongpin) || StringUtils.isEmpty(operator) || StringUtils.isEmpty(consumption)) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        Observable<ImageResult> entry = uploadImageData(images);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ImageResult>() {
                                    @Override
                                    public void onNext(ImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            ImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item);
                                            submitBinghaifangzhiData(file_urls, title, date, yongpin, operator, consumption, note);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitBinghaifangzhiData("", title, date, yongpin, operator, consumption, note);
                    }
                }
                break;
                case 2: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final String miaozhonglaiyuan = document_item2_edit.getText().toString();
                    final int miaozhong = Integer.parseInt(document_item3_edit.getTag().toString());
                    final String consumption = document_item4_edit.getText().toString();
                    final String note = document_note_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(date) || StringUtils.isEmpty(consumption) || StringUtils.isEmpty(operator) || miaozhong < 0) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        Observable<ImageResult> entry = uploadImageData(images);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ImageResult>() {
                                    @Override
                                    public void onNext(ImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            ImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item);
                                            submitToumiaoData(file_urls, title, date, miaozhonglaiyuan, miaozhong, consumption, note, operator);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitToumiaoData("", title, date, miaozhonglaiyuan, miaozhong, consumption, note, operator);
                    }
                }
                break;
                case 3: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final String siliaomingcheng = document_item2_edit.getText().toString();
                    final String goumaishang = document_item4_edit.getText().toString();
                    final String consumption = document_item5_edit.getText().toString();
                    final String note = document_note_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(siliaomingcheng) || StringUtils.isEmpty(goumaishang) || StringUtils.isEmpty(date) || StringUtils.isEmpty(consumption) || StringUtils.isEmpty(operator)) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        File take_phote = new File(photoUri.getPath());
                        Observable<WeishiImageResult> entry = uploadWeishiImageData(images, take_phote);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<WeishiImageResult>() {
                                    @Override
                                    public void onNext(WeishiImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            WeishiImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item.upload_files);
                                            String feed_url = gson.toJson(item.feed_pic);
                                            submitWeishiData(file_urls, feed_url, title, date, siliaomingcheng, goumaishang, consumption, note, operator);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitWeishiData("", "", title, date, siliaomingcheng, goumaishang, consumption, note, operator);
                    }
                }
                break;
                case 4: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final int miaozhong = Integer.parseInt(document_item3_edit.getTag().toString());
                    final String consumption = document_item4_edit.getText().toString();
                    final String note = document_note_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(date) || StringUtils.isEmpty(consumption) || StringUtils.isEmpty(operator) || miaozhong < 0) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        Observable<ImageResult> entry = uploadImageData(images);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ImageResult>() {
                                    @Override
                                    public void onNext(ImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            ImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item);
                                            submitBulaoData(file_urls, title, date, miaozhong, consumption, note, operator);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitBulaoData("", title, date, miaozhong, consumption, note, operator);
                    }
                }
                break;
                case 5: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final int miaozhong = Integer.parseInt(document_item2_edit.getTag().toString());
                    final String xiangmu = document_item3_edit.getText().toString();
                    final int jieguo = Integer.parseInt(document_item4_edit.getTag().toString());
                    final String note = document_note_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(date) || StringUtils.isEmpty(xiangmu) || StringUtils.isEmpty(operator) || miaozhong < 0 || jieguo < 0) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        Observable<ImageResult> entry = uploadImageData(images);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ImageResult>() {
                                    @Override
                                    public void onNext(ImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            ImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item);
                                            submitJianceData(file_urls, title, date, miaozhong, xiangmu, jieguo, note, operator);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitJianceData("", title, date, miaozhong, xiangmu, jieguo, note, operator);
                    }
                }
                break;

                case 6: {
                    final String title = document_item0_edit.getText().toString();
                    final String date = document_item1_edit.getText().toString();
                    final int guanlileixing = Integer.parseInt(document_item2_edit.getTag() != null ? document_item2_edit.getTag().toString() : "-1");
                    final String touruliang = document_item3_edit.getText().toString();
                    final String note = document_note_edit.getText().toString();
                    final String operator = document_operator_edit.getText().toString();
                    if (StringUtils.isEmpty(title) || StringUtils.isEmpty(date) || StringUtils.isEmpty(touruliang) || StringUtils.isEmpty(operator)) {
                        showError("请填写完整信息");
                        return;
                    }
                    if (imagepath != null && imagepath.size() > 0) {
                        ArrayList<File> images = new ArrayList<File>();
                        for (String i : imagepath) {
                            images.add(new File(i));
                        }
                        Observable<ImageResult> entry = uploadImageData(images);
                        entry.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ImageResult>() {
                                    @Override
                                    public void onNext(ImageResult uploadImgBean) {
                                        if (uploadImgBean.data.size() > 0) {
                                            ImageResult.Item item = uploadImgBean.data.get(0);
                                            Gson gson = new Gson();
                                            String file_urls = gson.toJson(item);
                                            submitRichangweihuData(file_urls, title, date, guanlileixing, touruliang, note, operator);
                                        } else {
                                            showError(uploadImgBean.message);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        Log.i(TAG, "onError: --->" + throwable.getMessage());
                                        showError(throwable.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onCompleted() {
                                        Log.i(TAG, "onComplete: ");
                                    }
                                });
                    } else {
                        submitRichangweihuData("", title, date, guanlileixing, touruliang, note, operator);
                    }
                }
                break;
            }
        }
    };

    private void submitBinghaifangzhiData(String fileurl, String title, String date, String yongpin, String operator, String consumption, String note) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_disease_prevention(access_token, filebag_numid, title, date, yongpin, operator, note, consumption, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private void submitToumiaoData(String fileurl, String title, String date, String miaozhonglaiyuan, int miaozhong, String consumption, String note, String operator) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_seedling(access_token, filebag_numid, miaozhong, title, date, miaozhonglaiyuan, operator, note, consumption, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private void submitWeishiData(String fileurl, String feed_pic, String title, String date, String siliaomingcheng, String goumaishang, String consumption, String note, String operator) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_feed(access_token, filebag_numid, title, date, consumption, operator, feed_pic, siliaomingcheng, goumaishang, note, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private void submitBulaoData(String fileurl, String title, String date, int miaozhong, String consumption, String note, String operator) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_fishing(access_token, filebag_numid, miaozhong, title, date, consumption, operator, note, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private void submitJianceData(String fileurl, String title, String date, int miaozhong, String xiangmu, int jieguo, String note, String operator) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_testing(access_token, filebag_numid, miaozhong, title, date, xiangmu, jieguo, operator, note, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private void submitRichangweihuData(String fileurl, String title, String date, int guanlileixing, String touruliang, String note, String operator) {
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doAdd_job_daily(access_token, filebag_numid, title, date, guanlileixing, touruliang, operator, note, fileurl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResultEntry>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable arg0) {
                Log.v(TAG, arg0.getLocalizedMessage());
            }

            @Override
            public void onNext(ResultEntry arg0) {
                Log.v(TAG, "message = " + arg0.message);
                if (arg0.success == 0) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showError(arg0.message);
                }
            }
        });
    }

    private Observable<ImageResult> uploadImageData(List<File> fileList) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token);
        int i = 0;
        for (File file : fileList) {
            builder.addFormDataPart("file_url[" + i++ + "]", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        }
        MultipartBody requestBody = builder.build();
        return RetrofitHelper.ServiceManager.getBaseImageService(getApplicationContext()).doAdd_job_pics(requestBody);
    }

    private Observable<WeishiImageResult> uploadWeishiImageData(List<File> fileList, File weishi) {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(ACCESS_TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token);
        int i = 0;
        for (File file : fileList) {
            builder.addFormDataPart("file_url[" + i++ + "]", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        }
        if (weishi.exists())
            builder.addFormDataPart("feed_pic", weishi.getName(), RequestBody.create(MediaType.parse("image/jpeg"), weishi));
        MultipartBody requestBody = builder.build();
        return RetrofitHelper.ServiceManager.getBaseImageService(getApplicationContext()).doAdd_job_feed_pics(requestBody);
    }

    private void showPreviewDialog() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inSampleSize = 8;
        Dialog dialog = new Dialog(this, R.style.Dialog_FullScreen);
        dialog.setContentView(R.layout.previe);
        ImageView imageView = dialog.findViewById(R.id.preview_view);
        imageView.setImageBitmap(BitmapFactory.decodeFile(photoUri.getPath(), options));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    private void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private String getTruePath(String absolutepath) {
        File file = new File(absolutepath);
        String filePath = file.getAbsolutePath();
        String[] dataStr = filePath.split("/");
        String fileTruePath = "/sdcard";
        for (int i = 4; i < dataStr.length; i++) {
            fileTruePath = fileTruePath + "/" + dataStr[i];
        }
        return fileTruePath;
    }

}
