package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-16.
 */

public class ChitanglistResult extends ResultEntry<ChitanglistResult.Item> {


    public class Item {
        public ArrayList<SubItem> AqBreedingGardenList;
    }

    public class SubItem {
        public AqBreedingGarden AqBreedingGarden;
    }

    public class AqBreedingGarden {
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
        public ArrayList<String> file_url;
    }
}
