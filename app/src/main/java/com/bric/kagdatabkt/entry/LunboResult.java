package com.bric.kagdatabkt.entry;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-9.
 */

public class LunboResult extends ResultEntry<LunboResult.Item> {

    public class Item {
        public ArrayList<SubItem> SupplierCarousel;
    }

    public class SubItem {
        public String file_url;
    }
}
