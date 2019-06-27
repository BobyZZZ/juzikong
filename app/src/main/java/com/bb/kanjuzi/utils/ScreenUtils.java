package com.bb.kanjuzi.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Boby on 2019/6/26.
 */

public class ScreenUtils {
    //    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentAndCoverStatusBar(Activity activity) {
        //FLAG_LAYOUT_NO_LIMITS这个千万别用，带虚拟按键的机型会有特别多问题

//        //FLAG_TRANSLUCENT_STATUS要求API大于19
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        //FLAG_LAYOUT_NO_LIMITS对API没有要求
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//设置这个全屏
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//这个设置状态栏透明
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Resources.getSystem().getColor(android.R.color.background_dark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int size = context.getResources().getDimensionPixelSize(resourceId);
        Log.e("zyc", "getStatusBarHeight: " + size);
        return size;
    }
}
