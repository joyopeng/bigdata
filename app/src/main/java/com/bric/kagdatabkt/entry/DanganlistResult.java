package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-16.
 */

public class DanganlistResult extends ResultEntry<DanganlistResult.Item> {


    public class Item {
        public Gardens gardens;
        public ArrayList<Job> jobs;
        public int page_count;
        public int page;
    }

    public class Job {
        public String numid;
        public String id;
        public String aq_file_bag_id;
        public String aq_job_type_id;
        public String control_date;
        public String consumption;
        public String operator;
        public String remark;
        public String created;
        public ArrayList<JobReportItem> reports;
    }

    public class JobReportItem {
        public AqJobReport AqJobReport;
    }

    public class AqJobReport {
        public String id;
        public String file_type_id;
        public String file_url;
    }


    public class Gardens {
        public String id;
        public String name;
        public String aq_breed_type_id;
        public String user_id;
        public String address;
        public String lat;
        public String lng;
        public String area;
        public String charge;
        public String tel;
        public String profile;
        public String status;
        public String created;
        public String modified;
        public String numid;
        //        public ArrayList<String> file_url;
        public String product_name_string;
        public String last_fishing;
        public String last_seedling;
        public String last_disease_prevention;
    }

    public class Product {
        public String id;
        public String name;
    }
}
