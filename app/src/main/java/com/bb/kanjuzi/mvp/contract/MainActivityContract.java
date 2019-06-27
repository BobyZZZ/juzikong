package com.bb.kanjuzi.mvp.contract;

import android.content.Context;
import android.content.Intent;
import android.icu.util.VersionInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/17.
 */

public interface MainActivityContract {
    interface View{
        void setSelectPage(int item);

        void setTab(ArrayList<Fragment> fragmentList, String[] titles);

        void showUpdateDialog(Bundle bundle);

        void refreshView(int collectSize);

        void showMessage(String msg);

        void setNavLikeCount(int size);
    }
//
//    public abstract class Presenter extends BasePresenter {
//        abstract void initData(Intent intent);
//
//        abstract void loadData();
//
//        abstract void clearCache();
//
//        abstract void getRecentVersionInfo();
//
//        abstract void compareVersionInfo(VersionInfo versionInfo);
//
//        abstract Intent buildFeedBackData(Context context);
//
//        abstract void refreshView(int collectSize);
//
//        abstract void querySentencesSize();
//
//        public abstract void showMessage(String msg);
//    }

}
