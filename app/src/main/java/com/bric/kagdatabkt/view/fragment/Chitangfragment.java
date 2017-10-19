package com.bric.kagdatabkt.view.fragment;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.utils.StringUtils;
import com.bric.kagdatabkt.ChitangAddActivity;
import com.bric.kagdatabkt.DanganAddChoseActivity;
import com.bric.kagdatabkt.R;
import com.bric.kagdatabkt.entry.ChitanglistResult;
import com.bric.kagdatabkt.entry.DanganlistResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.utils.ResourceUtils;
import com.bric.kagdatabkt.view.dialog.BaseAdapter;
import com.bric.kagdatabkt.view.dialog.BaseViewHolder;
import com.jiang.android.indicatordialog.IndicatorBuilder;
import com.jiang.android.indicatordialog.IndicatorDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joyopeng on 17-9-13.
 */

public class Chitangfragment extends Fragment implements View.OnClickListener {
    private static final String TAG = Chitangfragment.class.getSimpleName();

    //title
    private TextView base_toolbar_title;
    private ImageView base_nav_right;

    //content
    private MapView chitangaddressmap;
    private TextView chitang_title;
    private TextView chitang_area;
    private TextView chitang_owner;
    private TextView chitang_phonenumber;
    private TextView chitang_address;
    private TextView chitang_yangzhipinzhong;
    private TextView chitang_zuijintoumiao;
    private TextView chitang_zuijinxiaodu;
    private TextView chitang_zuijinbulao;
    private TextView chitang_zuijinzuoye;
    private TextView chitang_caozuoleixing;
    private TextView chitang_jiangcedanwei;
    private TextView chitang_caozuoneirong;

    private RelativeLayout chitang_empty;
    private LinearLayout chitang_content;
    private String access_token;

    private ArrayList<ChitanglistResult.SubItem> chitanglist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chitang_main, null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(CommonConstField.ACCESS_TOKEN, "");
        init(v);
        fetchChitangData();
        return v;
    }

    private void init(View v) {
        base_toolbar_title = (TextView) v.findViewById(R.id.base_toolbar_title);
        base_nav_right = (ImageView) v.findViewById(R.id.base_nav_right);

        chitangaddressmap = (MapView) v.findViewById(R.id.chitangaddressmap);
        chitang_title = (TextView) v.findViewById(R.id.chitang_title);
        chitang_area = (TextView) v.findViewById(R.id.chitang_area);
        chitang_owner = (TextView) v.findViewById(R.id.chitang_owner);
        chitang_phonenumber = (TextView) v.findViewById(R.id.chitang_phonenumber);
        chitang_address = (TextView) v.findViewById(R.id.chitang_address);
        chitang_yangzhipinzhong = (TextView) v.findViewById(R.id.chitang_yangzhipinzhong);
        chitang_zuijintoumiao = (TextView) v.findViewById(R.id.chitang_zuijintoumiao);
        chitang_zuijinxiaodu = (TextView) v.findViewById(R.id.chitang_zuijinxiaodu);
        chitang_zuijinbulao = (TextView) v.findViewById(R.id.chitang_zuijinbulao);
        chitang_zuijinzuoye = (TextView) v.findViewById(R.id.chitang_zuijinzuoye);
        chitang_caozuoleixing = (TextView) v.findViewById(R.id.chitang_caozuoleixing);
        chitang_jiangcedanwei = (TextView) v.findViewById(R.id.chitang_jiangcedanwei);
        chitang_caozuoneirong = (TextView) v.findViewById(R.id.chitang_caozuoneirong);
        chitang_empty = (RelativeLayout) v.findViewById(R.id.chitang_empty);
        chitang_content = (LinearLayout) v.findViewById(R.id.chitang_content);

        base_nav_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(getActivity(), ChitangAddActivity.class);
                startActivity(addintent);
            }
        });

        base_toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopDialog(v, 0.5f, IndicatorBuilder.GRAVITY_CENTER);
            }
        });
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

    private void fetchChitangData() {
        RetrofitHelper.ServiceManager.getBaseService().doGet_breeding_gardens(access_token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<ChitanglistResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        Log.v(TAG, arg0.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ChitanglistResult arg0) {
                        if (arg0.success == 0) {
                            Log.v(TAG, arg0.message);
                            chitanglist = arg0.data.get(0).AqBreedingGardenList;
                            if (chitanglist.size() > 0) {
                                getChitangById(chitanglist.get(0).AqBreedingGarden.numid);
                            } else {
                                chitang_empty.setVisibility(View.VISIBLE);
                                chitang_content.setVisibility(View.GONE);
                            }
                        }
                    }
                }
        );
    }

    private void getChitangById(final String garden_numid) {
        RetrofitHelper.ServiceManager.getBaseService().doGet_jobs(access_token, garden_numid, "1", "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<DanganlistResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable arg0) {
                        Log.v(TAG, arg0.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(DanganlistResult arg0) {
                        if (arg0.success == 0) {
                            Log.v(TAG, arg0.message);
                            fillData(arg0.data.get(0));
                        }
                    }
                }
        );
    }

    private void fillData(DanganlistResult.Item item) {
        DanganlistResult.Gardens garden = item.gardens;
        base_toolbar_title.setText(garden.name);
        LatLng latlng = new LatLng(Double.parseDouble(garden.lat), Double.parseDouble(garden.lng));
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(latlng, 24.0f);
        chitangaddressmap.getMap().animateMapStatus(mapStatus);
        chitang_title.setText(garden.name);
        chitang_area.setText(garden.area);
        chitang_owner.setText(garden.charge);
        chitang_phonenumber.setText(garden.tel);
        chitang_address.setText(garden.address);
        chitang_yangzhipinzhong.setText(garden.product_name_string);
        chitang_zuijintoumiao.setText(StringUtils.isEmpty(garden.last_seedling) ? "" : garden.last_seedling.substring(0, 10));
        chitang_zuijinxiaodu.setText(StringUtils.isEmpty(garden.last_disease_prevention) ? "" : garden.last_disease_prevention.substring(0, 10));
        chitang_zuijinbulao.setText(StringUtils.isEmpty(garden.last_fishing) ? "" : garden.last_fishing.substring(0, 10));


        DanganlistResult.Job job = item.jobs.get(0);
        chitang_zuijinzuoye.setText(job.control_date);
        Map<String, String> typeelement = ResourceUtils.getHashMapResource(getActivity(), R.xml.operate_type);
        chitang_caozuoleixing.setText(typeelement.get(job.aq_job_type_id));
//                            chitang_jiangcedanwei.setText(job.);
        chitang_caozuoneirong.setText(job.remark);
    }

    private void showTopDialog(View v, float v1, int gravityCenter) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        IndicatorDialog dialog = new IndicatorBuilder(getActivity())
                .width(600)
                .animator(R.style.dialog_exit)
                .height((int) (height * 0.5))
                .ArrowDirection(IndicatorBuilder.TOP)
                .bgColor(Color.WHITE)
                .gravity(gravityCenter)
                .dimEnabled(true)
                .ArrowRectage(v1)
                .radius(18)
                .layoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false))
                .adapter(new BaseAdapter() {
                    @Override
                    public void onBindView(BaseViewHolder holder, int position) {
                        TextView tv = holder.getView(R.id.item_add);
                        tv.setText(chitanglist.get(position).AqBreedingGarden.name);
                        if (position == chitanglist.size() - 1) {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
                        } else {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);

                        }
                    }

                    @Override
                    public int getLayoutID(int position) {
                        return R.layout.chitang_item;
                    }

                    @Override
                    public boolean clickable() {
                        return true;
                    }

                    @Override
                    public void onItemClick(View v, int position) {
                        getChitangById(chitanglist.get(position).AqBreedingGarden.numid);
                        new Thread() {
                            public void run() {
                                try {
                                    Instrumentation inst = new Instrumentation();
                                    inst.sendKeyDownUpSync(4);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }.start();
                    }

                    @Override
                    public int getItemCount() {
                        return chitanglist.size();
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(true);
        dialog.show(v);
    }
}
