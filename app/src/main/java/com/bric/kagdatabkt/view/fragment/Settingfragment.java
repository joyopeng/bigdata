package com.bric.kagdatabkt.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bric.kagdatabkt.LoginMainActivity;
import com.bric.kagdatabkt.MainActivity;
import com.bric.kagdatabkt.QiyexinxiAdd;
import com.bric.kagdatabkt.QrcodeListActivity;
import com.bric.kagdatabkt.R;
import com.bric.kagdatabkt.UpdatePasswordActivity;
import com.bric.kagdatabkt.utils.CommonConstField;

/**
 * Created by joyopeng on 17-9-13.
 */

public class Settingfragment extends Fragment implements View.OnClickListener {

    private RelativeLayout setting_persion_info_item;
    private RelativeLayout setting_login_password_item;
    private RelativeLayout setting_get_qrcode_item;
    private RelativeLayout setting_clear_cache_item;
    private Button exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting, null);
        init(v);
        addAction();
        return v;
    }

    private void init(View v) {
        setting_persion_info_item = (RelativeLayout) v.findViewById(R.id.setting_persion_info_item);
        setting_login_password_item = (RelativeLayout) v.findViewById(R.id.setting_login_password_item);
        setting_get_qrcode_item = (RelativeLayout) v.findViewById(R.id.setting_get_qrcode_item);
        setting_clear_cache_item = (RelativeLayout) v.findViewById(R.id.setting_clear_cache_item);
        exit = (Button) v.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent registerintent = new Intent(getActivity(), LoginMainActivity.class);
                startActivity(registerintent);
                getActivity().finish();
            }
        });
    }

    private void addAction() {
        setting_persion_info_item.setOnClickListener(this);
        setting_login_password_item.setOnClickListener(this);
        setting_get_qrcode_item.setOnClickListener(this);
        setting_clear_cache_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_persion_info_item: {
                Intent addintent = new Intent(getActivity(), QiyexinxiAdd.class);
                startActivity(addintent);
            }
            break;
            case R.id.setting_login_password_item: {
                Intent updatepasswordintent = new Intent(getActivity(), UpdatePasswordActivity.class);
                startActivity(updatepasswordintent);
            }
            break;
            case R.id.setting_get_qrcode_item: {
                Intent updatepasswordintent = new Intent(getActivity(), QrcodeListActivity.class);
                startActivity(updatepasswordintent);
            }
            break;
            case R.id.setting_clear_cache_item: {
            }
            break;
            default:
                break;
        }
    }
}
