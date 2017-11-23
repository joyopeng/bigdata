package com.bric.kagdatabkt.view.fragment;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bric.kagdatabkt.DanganAddActivity;
import com.bric.kagdatabkt.DanganAddChoseActivity;
import com.bric.kagdatabkt.DanganDetailActivity;
import com.bric.kagdatabkt.R;
import com.bric.kagdatabkt.entry.ChitanglistResult;
import com.bric.kagdatabkt.entry.DanganDetailResult;
import com.bric.kagdatabkt.entry.DanganlistResult;
import com.bric.kagdatabkt.net.RetrofitHelper;
import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.utils.ResourceUtils;
import com.bric.kagdatabkt.view.dialog.BaseAdapter;
import com.bric.kagdatabkt.view.dialog.BaseViewHolder;
import com.bric.kagdatabkt.view.dialog.FlipOverListView;
import com.jiang.android.indicatordialog.IndicatorBuilder;
import com.jiang.android.indicatordialog.IndicatorDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bric.kagdatabkt.utils.CommonConstField.JOB_ID;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_TYPE_ID_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.NUMID_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.NUMNAME_KEY;

/**
 * Created by joyopeng on 17-9-13.
 */

public class Danganfragment extends Fragment implements View.OnClickListener {

    private final String TAG = Danganfragment.class.getSimpleName();
    private TextView base_toolbar_title;
    private ImageView base_nav_right;
    private ImageView base_nav_back;
    private TextView filebag_numid;
    private FlipOverListView listView;
    private RelativeLayout dangan_empty;
    private LinearLayout dangan_content;
    private TextView jobs_filter;
    private TextView hint_emperty_text2;
    private TextView hint_emperty_text1;
    private String access_token;
    private ArrayList<ChitanglistResult.SubItem> chitanglist;
    private ArrayList<DanganlistResult.Job> jobs;
    Map<String, String> typeelement;
    private String numid;
    private String numName;
    private ArrayList<OperatorType> types = new ArrayList<>();
    private int lastVisibleIndex;
    private View footView;
    private View lastView;
    private int pageCount;
    private int currentPage = 1;
    private int currentJobId;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.document_main, null);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(CommonConstField.COMMON_PREFRENCE, 0);
        access_token = sharedPreferences.getString(CommonConstField.ACCESS_TOKEN, "");
        init(v);
        fetchChitangData();
        loadxmlData();
        typeelement = ResourceUtils.getHashMapResource(getActivity(), R.xml.operate_type);
        return v;
    }

    private void init(View v) {
        // headerIV = (ImageView) v.findViewById(R.id.person2_header_iv);
        base_toolbar_title = (TextView) v.findViewById(R.id.base_toolbar_title);
        base_nav_right = (ImageView) v.findViewById(R.id.base_nav_right);
        base_nav_back = (ImageView) v.findViewById(R.id.base_nav_back);
        base_nav_back.setVisibility(View.GONE);
        filebag_numid = (TextView) v.findViewById(R.id.filebag_numid);
        listView = (FlipOverListView) v.findViewById(R.id.dangan_jobs);
        dangan_empty = (RelativeLayout) v.findViewById(R.id.dangan_empty);
        dangan_content = (LinearLayout) v.findViewById(R.id.dangan_content);
        jobs_filter = (TextView) v.findViewById(R.id.jobs_filter);
        base_nav_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent(getActivity(), DanganAddChoseActivity.class);
                addintent.putExtra(NUMID_KEY, filebag_numid.getText());
                addintent.putExtra(NUMNAME_KEY, numName);
                startActivityForResult(addintent, 0);
            }
        });

        base_toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopDialog(v, 0.5f, IndicatorBuilder.GRAVITY_CENTER);
            }
        });

        jobs_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRightDialog(v, 0.2f, IndicatorBuilder.GRAVITY_RIGHT);
            }
        });
        hint_emperty_text2 = (TextView) v.findViewById(R.id.hint_emperty_text2);
        hint_emperty_text1 = (TextView) v.findViewById(R.id.hint_emperty_text1);
        footView = getActivity().getLayoutInflater().inflate(R.layout.footview, null);
        lastView = getActivity().getLayoutInflater().inflate(R.layout.lv_last_page_view, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.v(TAG, "ok");
            getChitangById(numid, 0, currentPage);
        }
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
        showProgressDialog();
        RetrofitHelper.ServiceManager.getBaseService(getActivity().getApplicationContext()).doGet_breeding_gardens(access_token)
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
                                numid = chitanglist.get(0).AqBreedingGarden.numid;
                                numName = chitanglist.get(0).AqBreedingGarden.name;
                                getChitangById(numid, 0, currentPage);
                            } else {
                                base_nav_right.setVisibility(View.GONE);
                                base_toolbar_title.setText("档案管理");
                            }
                        }else{
                            showError(arg0.message);
                        }
                    }
                }
        );
    }

    private void getChitangById(final String garden_numid, int jobtype_id, int page) {
        RetrofitHelper.ServiceManager.getBaseService(getActivity().getApplicationContext()).doGet_jobs(access_token, garden_numid, jobtype_id, page, 20)
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
                        if (dialog != null && dialog.isShowing())
                            dialog.dismiss();
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
        pageCount = item.page_count;
        currentPage = item.page;
        if (currentPage == 1) {
            jobs = item.jobs;
            base_toolbar_title.setText(garden.name + "管理");
            numName = garden.name;
            filebag_numid.setText(garden.numid);
            if (jobs.size() > 0) {
                dangan_empty.setVisibility(View.GONE);
                dangan_content.setVisibility(View.VISIBLE);
                listView.setAdapter(new MyAdspter());
                if (pageCount > 1) {
                    listView.setLoadingView(footView);
                    listView.setOnFilpOverListener(filpOverListener);
                }
            } else {
                dangan_empty.setVisibility(View.VISIBLE);
                dangan_content.setVisibility(View.GONE);
                hint_emperty_text1.setText("亲,您还没有添加档案额~");
                hint_emperty_text2.setVisibility(View.GONE);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent addintent = new Intent(getActivity(), DanganDetailActivity.class);
                    addintent.putExtra(JOB_TYPE_ID_KEY, jobs.get(position).aq_job_type_id);
                    addintent.putExtra(NUMID_KEY, numid);
                    addintent.putExtra(JOB_ID, jobs.get(position).id);
                    startActivity(addintent);
//                finish();
                }
            });
        } else {
            jobs.addAll(item.jobs);
            Log.v(TAG, "job size =" + jobs.size());
            ((MyAdspter) listView.getAdapter()).notifyDataSetChanged();
            listView.setLocked(false);
//            listView.setOnFilpOverListener(null);
            if (currentPage == pageCount) {
                listView.showEndPageView(lastView);
            }
        }
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
                        numid = chitanglist.get(position).AqBreedingGarden.numid;
                        jobs_filter.setText("全部");
                        listView.removeFooterView(lastView);
                        listView.setLocked(false);
                        getChitangById(numid, 0, currentPage);
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
                        return chitanglist != null ? chitanglist.size() : 0;
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(true);
        dialog.show(v);
    }

    private void showRightDialog(View v, float v1, int gravityCenter) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        IndicatorDialog dialog = new IndicatorBuilder(getActivity())
                .width(600)
                .animator(R.style.dialog_exit)
                .height((int) (height * 0.5))
                .ArrowDirection(IndicatorBuilder.RIGHT)
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
                        tv.setText(types.get(position).name);
                        if (position == types.size() - 1) {
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
                        jobs_filter.setText(types.get(position).name);
                        currentJobId = types.get(position).key;
                        listView.removeFooterView(lastView);
                        getChitangById(numid, currentJobId, currentPage);
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
                        return types.size();
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
            Log.v(TAG, "job size =" + jobs.size());
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
            holder.control_date_value.setText(job.control_date);
            if ("5".equals(job.aq_job_type_id)) {
                holder.operator_label.setText("检测单位:");
            } else {
                holder.operator_label.setText("操作人:");
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


    private void loadxmlData() {
        Map<String, String> typeelement = ResourceUtils.getHashMapResource(getActivity(), R.xml.operate_type);
        if (typeelement.size() > 0) {
            Set<Map.Entry<String, String>> sets = typeelement.entrySet();
            Iterator<Map.Entry<String, String>> its = sets.iterator();
            while (its.hasNext()) {
                Map.Entry<String, String> entry = its.next();
                OperatorType type = new OperatorType();
                type.key = Integer.parseInt(entry.getKey());
                type.name = entry.getValue();
                types.add(type);
            }
            OperatorType type = new OperatorType();
            type.key = 0;
            type.name = "全部";
            types.add(type);
        }

    }

    public class OperatorType {
        public String name;
        public int key;
    }

    FlipOverListView.FilpOverListener filpOverListener = new FlipOverListView.FilpOverListener() {
        @Override
        public boolean filpOverEvent() {
            int nextPage = currentPage + 1;
            if (nextPage > pageCount) {
                listView.setOnFilpOverListener(null);
                return false;
            } else {
                getChitangById(numid, currentJobId, nextPage);
            }
            return true;
        }
    };


    private void showProgressDialog() {
        dialog = new Dialog(getActivity(), R.style.Dialog_FullScreen);
        dialog.setContentView(R.layout.progress_view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
