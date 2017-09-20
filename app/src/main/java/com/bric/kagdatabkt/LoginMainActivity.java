package com.bric.kagdatabkt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.bric.kagdatabkt.utils.LocationService;

public class LoginMainActivity extends AppCompatActivity {

    private static final String TAG = LoginMainActivity.class.getSimpleName();
    private EditText login_name_edit;
    private EditText login_password_edit;
    private TextView login_forgetpassword;
    private TextView login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initView();
    }

    private void initView() {
        login_name_edit = (EditText) findViewById(R.id.login_name_edit);
        login_password_edit = (EditText) findViewById(R.id.login_password_edit);
        login_forgetpassword = (TextView) findViewById(R.id.login_forgetpassword);
        login_register = (TextView) findViewById(R.id.login_register);
        login_name_edit.setBackgroundResource(0);
        login_password_edit.setBackgroundResource(0);

    }
}
