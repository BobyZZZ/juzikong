package com.bb.kanjuzi.mvp.contract;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/17.
 */

public interface HomeFragmentContract {
    interface View {
        void setTab(ArrayList<Fragment> fragmentList, String[] titles);
    }

    interface Model {
        void getTabsAndFragment();
    }
}
