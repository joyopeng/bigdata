package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-9.
 */

public class WeishiImageResult extends ResultEntry<WeishiImageResult.Item> {

    public class Item {
        public Upload_files upload_files;
        public Feed_pic feed_pic;
    }

    public class Upload_files {
        public ArrayList<String> file_url;
    }

    public class Feed_pic {
        public String file_url;
    }
}
