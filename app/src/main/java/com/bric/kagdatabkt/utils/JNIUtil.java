package com.bric.kagdatabkt.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joyopeng on 17-11-8.
 */

public class JNIUtil {

    //    static{
//        System.loadLibrary("native-lib");
//    }
//
    public void loadLibrary() {
        System.loadLibrary("native-lib");
    }

    public native void showHelloWord(String content);

    public native int testAdd(int i, int j);

    public int testTtry() {

        File f = new File("");
        try {
            FileInputStream inputStream = new FileInputStream(f);
            return 0;
        } catch (FileNotFoundException e) {
        } finally {
            Log.v("aaaa", "dd");
            return 1;
        }
    }

}
