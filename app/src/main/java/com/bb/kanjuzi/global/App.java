package com.bb.kanjuzi.global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.bb.kanjuzi.greendao.DaoMaster;
import com.bb.kanjuzi.greendao.DaoSession;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

/**
 * Created by Boby on 2019/6/17.
 */

public class App extends MultiDexApplication {
    private static Context context;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGreenDao();
    }

    public static Context getContext() {
        return context;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "searchHistory.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
