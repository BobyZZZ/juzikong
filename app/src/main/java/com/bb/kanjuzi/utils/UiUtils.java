package com.bb.kanjuzi.utils;

import android.content.Context;

import com.bb.kanjuzi.global.App;

/**
 * Created by Boby on 2019/6/19.
 */

public class UiUtils {
    private static Context mContext = App.getContext();

    public static int dp2Px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
