package com.bric.kagdatabkt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.view.fragment.Chitangfragment;
import com.bric.kagdatabkt.view.fragment.Danganfragment;
import com.bric.kagdatabkt.view.fragment.HomeFragment;
import com.bric.kagdatabkt.view.fragment.Settingfragment;
import com.bric.kagdatabkt.view.layout.TabLayout;

import static com.bric.kagdatabkt.utils.CommonConstField.USER_NAME;

public class MainActivity extends FragmentActivity {

    private TabLayout tab_home;
    private TabLayout tab_chitang;
    private TabLayout tab_dangan;
    private TabLayout tab_setting;
    private FragmentManager mFM = null;

    private int currentTabid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionListener();
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        if (sharedPreferences != null && !StringUtils.isEmpty(sharedPreferences.getString(USER_NAME, ""))) {
        } else {
            Intent registerintent = new Intent(MainActivity.this, LoginMainActivity.class);
            startActivity(registerintent);
            finish();
        }
    }

    private void initView() {
        tab_home = (TabLayout) findViewById(R.id.tab_home);
        tab_chitang = (TabLayout) findViewById(R.id.tab_chitang);
        tab_dangan = (TabLayout) findViewById(R.id.tab_dangan);
        tab_setting = (TabLayout) findViewById(R.id.tab_setting);
        tab_home.setText(R.string.top_page);
        tab_home.setIcon(R.drawable.homepage_tab_default);
        tab_chitang.setText(R.string.title_chitang);
        tab_chitang.setIcon(R.drawable.chitang_tab_normal);
        tab_dangan.setText(R.string.title_dangan);
        tab_dangan.setIcon(R.drawable.dangan_tab_normal);
        tab_setting.setText(R.string.title_wode);
        tab_setting.setIcon(R.drawable.seting_tab_normal);
    }

    private void initActionListener() {
        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        tab_chitang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        tab_dangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });
        tab_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View var1) {
                var1.requestFocus();
            }
        });

        tab_home.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tab_home.setIcon(R.drawable.homepage_tab_focus);
                    tab_home.setTextColor(getResources().getColor(R.color.skyblue));
                    changeHomepage();
                } else {
                    tab_home.setIcon(R.drawable.homepage_tab_default);
                    tab_home.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        tab_chitang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tab_chitang.setIcon(R.drawable.chitang_tab_focus);
                    tab_chitang.setTextColor(getResources().getColor(R.color.skyblue));
                    changechitang();
                } else {
                    tab_chitang.setIcon(R.drawable.chitang_tab_normal);
                    tab_chitang.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        tab_dangan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tab_dangan.setIcon(R.drawable.dangan_tab_focus);
                    tab_dangan.setTextColor(getResources().getColor(R.color.skyblue));
                    changdangan();
                } else {
                    tab_dangan.setIcon(R.drawable.dangan_tab_normal);
                    tab_dangan.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });

        tab_setting.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    tab_setting.setIcon(R.drawable.seting_tab_focus);
                    tab_setting.setTextColor(getResources().getColor(R.color.skyblue));
                    changSeting();
                } else {
                    tab_setting.setIcon(R.drawable.seting_tab_normal);
                    tab_setting.setTextColor(getResources().getColor(R.color.lightgrey));
                }
            }
        });
    }

    private void changeHomepage() {
        currentTabid = CommonConstField.TABHOST_TAB_HOMEPAGE_ID;
        Fragment f = new HomeFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commitAllowingStateLoss();
    }


    private void changechitang() {
        currentTabid = CommonConstField.TABHOST_TAB_CHITANG_ID;
        Fragment f = new Chitangfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commitAllowingStateLoss();
    }

    private void changdangan() {
        currentTabid = CommonConstField.TABHOST_TAB_DANGAN_ID;
        Fragment f = new Danganfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commitAllowingStateLoss();
    }

    private void changSeting() {
        currentTabid = CommonConstField.TABHOST_TAB_SETTING_ID;
        Fragment f = new Settingfragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.base_content, f);
        ft.commitAllowingStateLoss();
    }

    public void trigedanganClick() {
        tab_dangan.performClick();
    }

    public void trigechitangClick() {
        tab_chitang.performClick();
    }
}
