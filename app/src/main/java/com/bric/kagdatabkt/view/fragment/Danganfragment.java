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
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.NUMID_KEY;

/**
 * Created by joyopeng on 17-9-13.
 */

public class Danganfragment extends Fragment implements View.OnClickListener {

    private final String TAG = Danganfragment.class.getSimpleName();
    private TextView base_toolbar_title;
    private ImageView base_nav_right;
    private TextView filebag_numid;
    private ListView listView;

    private String access_token;
    private ArrayList<ChitanglistResult.SubItem> chitanglist;
    private ArrayList<DanganlistResult.Job> jobs;
    Map<String, String> typeelement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.document_main, null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(CommonConstField.ACCESS_TOKEN, "");
        init(v);
        fetchChitangData();
        typeelement = ResourceUtils.getHashMapResource(getActivity(), R.xml.operate_type);
        return v;
    }

    private void init(View v) {
        // headerIV = (ImageView) v.findViewById(R.id.person2_header_iv);
        base_toolbar_title = (TextView) v.findViewById(R.id.base_toolbar_title);
        base_nav_right = (ImageView) v.findViewById(R.id.base_nav_right);
        filebag_numid = (TextView) v.findViewById(R.id.filebag_numid);
        listView = (ListView) v.findViewById(R.id.dangan_jobs);
        base_nav_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(getActivity(), DanganAddChoseActivity.class);
                addintent.putExtra(NUMID_KEY, filebag_numid.getText());
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
                            getChitangById(chitanglist.get(0).AqBreedingGarden.numid);
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
        jobs = item.jobs;
        base_toolbar_title.setText(garden.name);
        filebag_numid.setText(garden.numid);
        listView.setAdapter(new MyAdspter());
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


    class MyAdspter extends android.widget.BaseAdapter {

        private LayoutInflater layoutInflater;

        public MyAdspter() {
            this.layoutInflater = LayoutInflater.from(getActivity());
        }

        /**
         * 组件集合，对应list.xml中的控件
         *
         * @author Administrator
         */
        public final class PlaceHolder {
            public ImageView operator_type_icon;
            public TextView operator_type_value;
            public TextView control_date_label;
            public TextView control_date_value;
            public TextView operator_label;
            public TextView operator_value;
        }

        @Override
        public int getCount() {
            return jobs.size();
        }

        /**
         * 获得某一位置的数据
         */
        @Override
        public Object getItem(int position) {
            return jobs.get(position);
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
                convertView = layoutInflater.inflate(R.layout.dangan_item, null);
                holder.operator_type_icon = (ImageView) convertView.findViewById(R.id.operator_type_icon);
                holder.operator_type_value = (TextView) convertView.findViewById(R.id.operator_type_value);
                holder.control_date_label = (TextView) convertView.findViewById(R.id.control_date_label);
                holder.control_date_value = (TextView) convertView.findViewById(R.id.control_date_value);
                holder.operator_label = (TextView) convertView.findViewById(R.id.operator_label);
                holder.operator_value = (TextView) convertView.findViewById(R.id.operator_value);
                convertView.setTag(holder);
            } else {
                holder = (PlaceHolder) convertView.getTag();
            }
            DanganlistResult.Job job = jobs.get(position);
            holder.operator_type_icon.setBackgroundResource(getIconByType(Integer.parseInt(job.aq_job_type_id)));
            holder.operator_type_value.setText(typeelement.get(job.aq_job_type_id));
//            if("5".equals(job.aq_job_type_id)){
//                holder.control_date_label.setText("检测单位");
//            }
            holder.control_date_value.setText(job.control_date);
            if ("5".equals(job.aq_job_type_id)) {
                holder.operator_label.setText("检测单位:");
            }
            holder.operator_value.setText(job.operator);
            return convertView;
        }

    }

    private int getIconByType(int typeid) {
        switch (typeid) {
            case 1:
                return R.drawable.bingchongfangzhi;
            case 2:
                return R.drawable.toumiao;
            case 3:
                return R.drawable.weishi;
            case 4:
                return R.drawable.bulao;
            case 5:
                return R.drawable.jiance;
            case 6:
                return R.drawable.richangguanli;
            default:
                return R.drawable.bingchongfangzhi;
        }
    }

}
