package com.bb.kanjuzi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bb.kanjuzi.global.App;

/**
 * Created by Boby on 2019/6/25.
 */

public class SharePreferenceUtils {
    public static final String HISTORY_NAME = "history_name";
    public static final String HISTORY_HREF = "history_href";

    private static SharedPreferences sp;

    public static String getString(String key, String defaultValue) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("search_history", Context.MODE_PRIVATE);
        }
        String string = sp.getString(key, defaultValue);
        return string;
    }

    public static void putString(String key, String value) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("search_history", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("app_theme", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        if (sp == null) {
            sp = App.getContext().getSharedPreferences("app_theme", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }
}
