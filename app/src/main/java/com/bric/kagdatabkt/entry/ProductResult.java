package com.bric.kagdatabkt.entry;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.ArrayList;

/**
 * Created by joyopeng on 17-10-18.
 */

public class ProductResult extends ResultEntry<ProductResult.Item> {
    public class Item {
        public ArrayList<SubItem> List;
    }

    public class SubItem implements IPickerViewData {
        public String id;
        public String name;

        public String getPickerViewText() {
            return name;
        }
    }
}
