package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-9.
 */

public class QrcodeListResult extends ResultEntry<QrcodeListResult.Item> {

    public class Item {
        public ArrayList<SubItem> List;
    }


    public class SubItem {
        public String id;
        public String aq_file_bag_id;
        public String aq_job_type_id;
        public String aq_breed_product_id;
        public String control_date;
        public String consumption;
        public String operator;
        public String remark;
        public String created;
        public String modified;
        public String numid;
        public String bag_name;
        public String report;
        public String product_name;
        public int has_apply_qrcode_count;
    }
}
