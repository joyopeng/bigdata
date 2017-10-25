package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-16.
 */

public class DanganDetailResult extends ResultEntry<DanganDetailResult.Item> {


    public class Item {
        public AqFileBag AqFileBag;
        public Job Job;
        public AqBreedProduct AqBreedProduct;
        public ArrayList<String> AqJobReport;
    }

    public class Job {
        public String numid;
        public String id;
        public String aq_file_bag_id;
        public String aq_job_type_id;
        public String aq_breed_product_id;
        public String title;
        public String control_date;
        public String source;
        public String consumption;
        public String operator;
        public String remark;
        public String created;
        public String supplies;//病害防治属性
        public String feed_name;//喂食属性
        public String feed_pic;//喂食属性
        public String buyer;//喂食属性
        public String project_name;//检测属性
        public String result;//检测属性
        public String daily_type_id;//日常管理属性


    }

    public class AqJobReport {
        public String id;
        public String file_type_id;
        public String file_url;
    }


    public class AqFileBag {
        public String id;
        public String name;
        public String user_id;
        public String status;
        public String created;
        public String modified;
        public String numid;
    }

    public class AqBreedProduct {
        public String id;
        public String name;
        public String aq_breed_type_id;
        public String status;
        public String created;
        public String modified;
    }
}
