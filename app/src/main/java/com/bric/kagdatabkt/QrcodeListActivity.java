package com.bric.kagdatabkt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.entry.ChitanglistResult;
import com.bric.kagdatabkt.entry.QrcodeListResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_FISHING_ID;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_ID;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_TYPE_ID_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.NUMID_KEY;

public class QrcodeListActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    private static final String TAG = QrcodeListActivity.class.getSimpleName();

    private ImageView base_nav_back;
    private ImageView base_nav_right;
    private TextView base_toolbar_title;
    private ListView codelistView;

    private ArrayList<QrcodeListResult.SubItem> qrcodelist;
    private Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_list);
        picasso = Picasso.with(this);
        initView();
        fetchChitangData();
    }

    private void initView() {
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        base_nav_right = (ImageView) findViewById(R.id.base_nav_right);
        base_nav_right.setVisibility(View.GONE);
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setText("申请二维码");
        base_toolbar_title.setCompoundDrawables(null, null, null, null);
        codelistView = (ListView) findViewById(R.id.qrcode_list);
        codelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addintent = new Intent(QrcodeListActivity.this, DanganDetailActivity.class);
                addintent.putExtra(JOB_FISHING_ID, qrcodelist.get(position).id);
                startActivity(addintent);
            }
        });
        codelistView.setOnScrollListener(this);
    }

    private void fetchChitangData() {
        SharedPreferences sharedPreferences = getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        String access_token = sharedPreferences.getString(CommonConstField.ACCESS_TOKEN, "");
        RetrofitHelper.ServiceManager.getBaseService(getApplicationContext()).doGet_apply_qrcode_list(access_token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<QrcodeListResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        Log.v(TAG, arg0.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(QrcodeListResult arg0) {
                        if (arg0.success == 0) {
                            qrcodelist = arg0.data.get(0).List;
                            codelistView.setAdapter(new MyAdspter());
                        }
                    }
                }
        );
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            //如果在暂停或者触摸的情况下完成重置
            picasso.resumeTag(this);
        } else {
            //停止更新
            picasso.pauseTag(this);
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    class MyAdspter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;

        public MyAdspter() {
            this.layoutInflater = LayoutInflater.from(QrcodeListActivity.this);
        }

        /**
         * 组件集合，对应list.xml中的控件
         *
         * @author Administrator
         */
        public final class PlaceHolder {
            public TextView qrcode_gardenname;
            public TextView qrcode_applystatus;
            public ImageView qrcode_report;
            public TextView control_date;
            public TextView qrcode_product_name;
            public TextView qrcode_consumption;
            public TextView qrcode_operator;
            public Button qrcode_submit;
        }

        @Override
        public int getCount() {
            return qrcodelist.size();
        }

        /**
         * 获得某一位置的数据
         */
        @Override
        public Object getItem(int position) {
            return qrcodelist.get(position);
        }

        /**
         * 获得唯一标识
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PlaceHolder holder;
            if (convertView == null) {
                holder = new PlaceHolder();
                //获得组件，实例化组件
                convertView = layoutInflater.inflate(R.layout.qrcode_item, null);
                holder.qrcode_gardenname = (TextView) convertView.findViewById(R.id.qrcode_gardenname);
                holder.qrcode_applystatus = (TextView) convertView.findViewById(R.id.qrcode_applystatus);
                holder.qrcode_report = (ImageView) convertView.findViewById(R.id.qrcode_report);
                holder.control_date = (TextView) convertView.findViewById(R.id.control_date);
                holder.qrcode_product_name = (TextView) convertView.findViewById(R.id.qrcode_product_name);
                holder.qrcode_consumption = (TextView) convertView.findViewById(R.id.qrcode_consumption);
                holder.qrcode_operator = (TextView) convertView.findViewById(R.id.qrcode_operator);
                holder.qrcode_submit = (Button) convertView.findViewById(R.id.qrcode_submit);
                convertView.setTag(holder);
            } else {
                holder = (PlaceHolder) convertView.getTag();
            }
            QrcodeListResult.SubItem item = qrcodelist.get(position);
            holder.qrcode_gardenname.setText(item.bag_name);
            holder.qrcode_applystatus.setText((item.has_apply_qrcode_count > 0 ? "已申请" : "未申请"));
            if (StringUtils.isEmpty(item.report))
                item.report = "http://nmu.yy/files/pics/AqUserInfoReport/15077965457533.png";
            Picasso.with(QrcodeListActivity.this).load(item.report).tag(QrcodeListActivity.this).config(Bitmap.Config.RGB_565).resize(64, 64).into(holder.qrcode_report);
            holder.control_date.setText(item.control_date);
            holder.qrcode_product_name.setText(item.product_name);
            holder.qrcode_consumption.setText(item.consumption);
            holder.qrcode_operator.setText(item.operator);
            holder.qrcode_submit.setText((item.has_apply_qrcode_count > 0 ? "再申请" : "申请"));
            holder.qrcode_submit.setTag(position);
            holder.qrcode_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(v.getTag().toString());
                    Intent addintent = new Intent(QrcodeListActivity.this, QrcodeApplyActivity.class);
                    addintent.putExtra(JOB_FISHING_ID, qrcodelist.get(position).id);
                    addintent.putExtra(NUMID_KEY, qrcodelist.get(position).numid);
                    startActivity(addintent);
                }
            });
            return convertView;
        }

    }
}
