package com.bric.kagdatabkt.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bric.kagdatabkt.LoginMainActivity;
import com.bric.kagdatabkt.MainActivity;
import com.bric.kagdatabkt.QrcodeListActivity;
import com.bric.kagdatabkt.R;
import com.bric.kagdatabkt.entry.LunboResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.kcode.autoscrollviewpager.view.AutoScrollViewPager;
import com.kcode.autoscrollviewpager.view.BaseViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joyopeng on 17-9-12.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

//    private String[] paths = {"https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=c493b482b47eca800d053ee7a1229712/8cb1cb1349540923abd671df9658d109b2de49d7.jpg",
//            "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=ff0999f6d4160924c325a51be406359b/86d6277f9e2f070861ccd4a0ed24b899a801f241.jpg"};

    private AutoScrollViewPager viewPager;
    private BaseViewPagerAdapter<String> mBaseViewPagerAdapter;
    private MainActivity hostactivity;

    private LinearLayout chitanggaikuang;
    private LinearLayout danganguanli;
    private LinearLayout tianjiadangan;
    private LinearLayout erweimashenqing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homepage_main, null);
        hostactivity = (MainActivity) getActivity();
        init(v);
        return v;
    }

    private void init(View v) {
        viewPager = (AutoScrollViewPager) v.findViewById(R.id.viewPager);
        chitanggaikuang = (LinearLayout) v.findViewById(R.id.chitanggaikuang);
        danganguanli = (LinearLayout) v.findViewById(R.id.danganguanli);
        tianjiadangan = (LinearLayout) v.findViewById(R.id.tianjiadangan);
        erweimashenqing = (LinearLayout) v.findViewById(R.id.erweimashenqing);
        chitanggaikuang.setOnClickListener(this);
        danganguanli.setOnClickListener(this);
        tianjiadangan.setOnClickListener(this);
        erweimashenqing.setOnClickListener(this);
        mBaseViewPagerAdapter = new BaseViewPagerAdapter<String>(getActivity(), listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Picasso.with(getActivity()).load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {
            }
        };
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chitanggaikuang: {
                hostactivity.trigechitangClick();
            }
            break;
            case R.id.danganguanli: {
                hostactivity.trigedanganClick();
            }
            break;
            case R.id.tianjiadangan: {

            }
            break;
            case R.id.erweimashenqing: {
                Intent registerintent = new Intent(getActivity(), QrcodeListActivity.class);
                startActivity(registerintent);
            }
            break;
            default:
                break;
        }
    }

    private BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener = new BaseViewPagerAdapter.OnAutoViewPagerItemClickListener<String>() {

        @Override
        public void onItemClick(int position, String url) {
        }
    };

    private void initData() {
        RetrofitHelper.ServiceManager.getBaseService().doGet_supplier_carousels()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<LunboResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                    }

                    @Override
                    public void onNext(LunboResult arg0) {
                        if (arg0.success == 0) {
                            ArrayList<LunboResult.SubItem> items = arg0.data.get(0).SupplierCarousel;
                            List<String> data = new ArrayList<>();
                            for (int i = 0; i < items.size(); i++) {
                                data.add(items.get(i).file_url);
                            }
                            viewPager.setAdapter(mBaseViewPagerAdapter);
                            mBaseViewPagerAdapter.add(data);
                        }
                    }
                }
        );
    }
}
