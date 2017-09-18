package com.bric.kagdatabkt.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bric.kagdatabkt.R;

/**
 * Created by joyopeng on 17-9-13.
 */

public class Chitangfragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chitang_main, null);
        init(v);
        return v;
    }

    private void init(View v) {
        // headerIV = (ImageView) v.findViewById(R.id.person2_header_iv);
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
}
