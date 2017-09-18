package com.bric.kagdatabkt.view.fragment;

import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bric.kagdatabkt.R;
import com.kcode.autoscrollviewpager.view.AutoScrollViewPager;
import com.kcode.autoscrollviewpager.view.BaseViewPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyopeng on 17-9-12.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private String[] paths = {"https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=c493b482b47eca800d053ee7a1229712/8cb1cb1349540923abd671df9658d109b2de49d7.jpg",
            "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=ff0999f6d4160924c325a51be406359b/86d6277f9e2f070861ccd4a0ed24b899a801f241.jpg"};

    private AutoScrollViewPager viewPager;
    private BaseViewPagerAdapter<String> mBaseViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homepage_main, null);
        init(v);
        return v;
    }

    private void init(View v) {
        viewPager = (AutoScrollViewPager) v.findViewById(R.id.viewPager);
        mBaseViewPagerAdapter = new BaseViewPagerAdapter<String>(getActivity(), listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Picasso.with(getActivity()).load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {
            }
        };
        viewPager.setAdapter(mBaseViewPagerAdapter);

        mBaseViewPagerAdapter.add(initData());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // case R.id.person2_shopping: // °ŽÅ¥²Ù×÷
            // break;
            default:
                break;
        }
    }

    private BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener = new BaseViewPagerAdapter.OnAutoViewPagerItemClickListener<String>() {

        @Override
        public void onItemClick(int position, String url) {
        }
    };

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            data.add(paths[i]);
        }
        return data;
    }
}
