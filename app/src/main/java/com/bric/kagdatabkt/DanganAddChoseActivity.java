package com.bric.kagdatabkt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bric.kagdatabkt.utils.CommonConstField;
import com.bric.kagdatabkt.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bric.kagdatabkt.utils.CommonConstField.DANGAN_CONTENT_TYPE_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.JOB_TYPE_ID_KEY;
import static com.bric.kagdatabkt.utils.CommonConstField.NUMID_KEY;

/**
 * Created by joyopeng on 17-9-14.
 */

public class DanganAddChoseActivity extends FragmentActivity {

    private TextView base_toolbar_title;
    private ListView typelistview;
    private TextView addoperator_documentid;
    private ArrayList<OperatorType> types = new ArrayList<>();
    private String filebag_numid;
    private ImageView base_nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danganaddchose);
        filebag_numid = getIntent().getStringExtra(NUMID_KEY);
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setText("东塘管理");
        typelistview = (ListView) findViewById(R.id.typelistview);
        addoperator_documentid = (TextView) findViewById(R.id.addoperator_documentid);
        addoperator_documentid.setText(filebag_numid);
        base_nav_back = (ImageView) findViewById(R.id.base_nav_back);
        base_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        typelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent addintent = new Intent(DanganAddChoseActivity.this, DanganAddActivity.class);
                addintent.putExtra(JOB_TYPE_ID_KEY, types.get(i).key);
                addintent.putExtra(NUMID_KEY, filebag_numid);
                startActivityForResult(addintent,0);
//                finish();
            }
        });
        loadxmlData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Log.v("aaaa", "ok");
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private void loadxmlData() {
        Map<String, String> typeelement = ResourceUtils.getHashMapResource(this, R.xml.operate_type);
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
        }
        ListViewAdapter adapter = new ListViewAdapter(this, types);
        typelistview.setAdapter(adapter);

    }

    public class ListViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<OperatorType> types;

        public ListViewAdapter(Context context, ArrayList<OperatorType> maps) {
            inflater = LayoutInflater.from(context);
            types = maps;
        }

        @Override
        public int getCount() {
            return types.size();
        }

        @Override
        public Object getItem(int position) {
            return types.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.operator_type_item, parent, false);
                holder.type = convertView.findViewById(R.id.operator_type_id_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.type = convertView.findViewById(R.id.operator_type_id_text);
            }
            holder.type.setText(types.get(position).name);
            return convertView;
        }

        private class ViewHolder {
            TextView type;
        }
    }

    public class OperatorType {
        public String name;
        public int key;
    }
}
