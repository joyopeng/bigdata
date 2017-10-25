package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-9.
 */

public class QrcodeInfoResult extends ResultEntry<QrcodeInfoResult.Item> {

    public class Item {
        public AqJobFishing AqJobFishing;
        public AqFileBag AqFileBag;
        public AqBreedProduct AqBreedProduct;
        public AqApplyQrcode AqApplyQrcode;
    }

    public class AqJobFishing {
        public String id;
        public String aq_file_bag_id;
        public String aq_job_type_id;
        public String aq_breed_product_id;
        public String title;
        public String control_date;
        public String consumption;
        public String operator;
        public String remark;
        public String created;
        public String modified;
        public String apply_qrcode_count;
        public String numid;
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

    public class SubItem {
        public String id;
        public String aq_job_fishing_id;
        public String quantity;
        public String created;
        public String modified;
    }

    public class AqApplyQrcode {
        public ArrayList<SubItem> AqApplyQrcode;
    }
}
