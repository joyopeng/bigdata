package com.bric.kagdatabkt;

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
import android.widget.ListView;
import android.widget.TextView;

import com.bric.kagdatabkt.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bric.kagdatabkt.utils.CommonConstField.DANGAN_CONTENT_TYPE_KEY;

/**
 * Created by joyopeng on 17-9-14.
 */

public class DanganAddChoseActivity extends FragmentActivity {

    private TextView base_toolbar_title;
    private ListView typelistview;
    private ArrayList<OperatorType> types = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danganaddchose);
        base_toolbar_title = (TextView) findViewById(R.id.base_toolbar_title);
        base_toolbar_title.setText("东塘管理");
        typelistview = (ListView) findViewById(R.id.typelistview);
        typelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent addintent = new Intent(DanganAddChoseActivity.this, DanganAddActivity.class);
                addintent.putExtra(DANGAN_CONTENT_TYPE_KEY,types.get(i).key);
                startActivity(addintent);
            }
        });
        loadxmlData();
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

    private class OperatorType {
        public String name;
        public int key;
    }
}
