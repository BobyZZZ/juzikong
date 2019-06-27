package com.bb.kanjuzi.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.bb.kanjuzi.global.App;

/**
 * Created by Boby on 2019/6/25.
 */

public class ClipboardUtils {
    private static ClipboardManager manager;

    public static ClipboardManager getInstance(Context context) {
        if (manager == null) {
            manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        return manager;
    }

    public static String getClipboard() {
        if (manager == null) {
            manager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        }

        ClipData clipData = manager.getPrimaryClip();
        int itemCount = clipData.getItemCount();
        if (itemCount > 0) {
            return clipData.getItemAt(0).getText().toString();
        }
        return "";
    }

    public static void setClipboard(String text) {
        if (manager == null) {
            manager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        }

        ClipData clipData = ClipData.newPlainText("lable", text);
        manager.setPrimaryClip(clipData);
    }
}
