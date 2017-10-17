package com.bric.kagdatabkt;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.view.fragment.Chitangfragment;
import com.bric.kagdatabkt.view.fragment.Danganfragment;
import com.bric.kagdatabkt.view.fragment.HomeFragment;
import com.bric.kagdatabkt.view.fragment.Settingfragment;

public class MainActivity extends FragmentActivity {

    //    private ImageView base_nav_right;
//    private TextView base_toolbar_title;
    private ImageView homepage_tab_img;
    private TextView homepage_tab_text;
    private ImageView chitang_tab_img;
    private TextView chitang_tab_text;
    private ImageView dangan_tab_img;
    private TextView dangan_tab_text;
    private ImageView seting_tab_img;
    private TextView seting_tab_text;
    private FragmentManager mFM = null;

    private int currentTabid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionListener();
    }

    private void initView() {
//        base_nav_right = (ImageView) findViewById(R.id.base_nav_right);
//        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        homepage_tab_img = (ImageView) findViewById(R.id.homepage_tab_img);
        homepage_tab_text = (TextView) findViewById(R.id.homepage_tab_text);
        chitang_tab_img = (ImageView) findViewById(R.id.chitang_tab_img);
        chitang_tab_text = (TextView) findViewById(R.id.chitang_tab_text);
        dangan_tab_img = (ImageView) findViewById(R.id.dangan_tab_img);
        dangan_tab_text = (TextView) findViewById(R.id.dangan_tab_text);
        seting_tab_img = (ImageView) findViewById(R.id.seting_tab_img);
        seting_tab_text = (TextView) findViewById(R.id.tabhost_text);
    }

    private void initActionListener() {
        homepage_tab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        chitang_tab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        dangan_tab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        seting_tab_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });

        homepage_tab_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundResource(R.drawable.homepage_tab_focus);
                    homepage_tab_text.setTextColor(getResources().getColor(R.color.skyblue));
                    changeHomepage();
                } else {
                    view.setBackgroundResource(R.drawable.homepage_tab_default);
                    homepage_tab_text.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        chitang_tab_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundResource(R.drawable.chitang_tab_focus);
                    chitang_tab_text.setTextColor(getResources().getColor(R.color.skyblue));
                    changechitang();
                } else {
                    view.setBackgroundResource(R.drawable.chitang_tab_normal);
                    chitang_tab_text.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        dangan_tab_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundResource(R.drawable.dangan_tab_focus);
                    dangan_tab_text.setTextColor(getResources().getColor(R.color.skyblue));
                    changdangan();
                } else {
                    view.setBackgroundResource(R.drawable.dangan_tab_normal);
                    dangan_tab_text.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        seting_tab_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.setBackgroundResource(R.drawable.seting_tab_focus);
                    seting_tab_text.setTextColor(getResources().getColor(R.color.skyblue));
                    changSeting();
                } else {
                    view.setBackgroundResource(R.drawable.seting_tab_normal);
                    seting_tab_text.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

//        base_nav_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent addintent = new Intent(MainActivity.this, ChitangAddActivity.class);
//                if (currentTabid == CommonConstField.TABHOST_TAB_CHITANG_ID) {
//                    addintent = new Intent(MainActivity.this, ChitangAddActivity.class);
//                } else if (currentTabid == CommonConstField.TABHOST_TAB_DANGAN_ID) {
//                    addintent = new Intent(MainActivity.this, DanganAddChoseActivity.class);
//                }
//                startActivity(addintent);
//            }
//        });
    }

    private void changeHomepage() {
        currentTabid = CommonConstField.TABHOST_TAB_HOMEPAGE_ID;
        Fragment f = new HomeFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commit();
    }


    private void changechitang() {
        currentTabid = CommonConstField.TABHOST_TAB_CHITANG_ID;
        Fragment f = new Chitangfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commit();
    }

    private void changdangan() {
        currentTabid = CommonConstField.TABHOST_TAB_DANGAN_ID;
        Fragment f = new Danganfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commit();
    }

    private void changSeting() {
        currentTabid = CommonConstField.TABHOST_TAB_SETTING_ID;
        Fragment f = new Settingfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commit();
    }
}
