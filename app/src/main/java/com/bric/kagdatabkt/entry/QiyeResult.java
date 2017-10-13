package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-9.
 */

public class QiyeResult extends ResultEntry<QiyeResult.Item> {

    public class Item {
        public User_info user_info;
        public ArrayList<SubItem> reports;
    }


    public class SubItem {
        public AqUserInfoReport AqUserInfoReport;
    }

    public class AqUserInfoReport {
        public String id;
        public String aq_user_info_id;
        public String type_id;
        public String file_url;
        public String file_type_id;
        public String created;
        public String modified;
    }

    public class User_info {
        public String id;
        public String company_name;
        public String user_id;
        public String company_profile;
        public String company_qualification;
        public String status;
        public String ipv4;
        public String created;
        public String modified;
    }

}
