package com.bb.kanjuzi.utils;

import android.content.Context;

import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.mvp.view.MainActivity;

/**
 * Created by Boby on 2019/6/17.
 */

public class ResUtils {
    private static Context mContext = App.getContext();

    public static String[] getStringArray(int id) {
        return mContext.getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return mContext.getResources().getColor(id);
    }
}
