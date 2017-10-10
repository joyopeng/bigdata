package com.bric.kagdatabkt.entry;

import java.util.List;

/**
 * Created by joyopeng on 17-10-9.
 */

public class ResultEntry<T> {
    public int success;
    public String message;
    public List<T> data;

}
