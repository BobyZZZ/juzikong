package com.bb.kanjuzi.mvp.presenter;

import android.support.v4.app.Fragment;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.mvp.contract.HomeFragmentContract;
import com.bb.kanjuzi.mvp.model.HomeSquareModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.HomeSquareFragment;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.mvp.view.SquareTabFragment;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/17.
 */

public class HomeSquareFragmentPresenter extends BasePresenter<HomeSquareFragment> {

    HomeSquareModel mModel;
    public HomeSquareFragmentPresenter() {
    }

    public void process() {
//        getView().setTab(mFragments, mTabs);
        mModel.getTabsAndFragment();
    }

    public void initData() {
        mModel = new HomeSquareModel(this);
    }

    public void onGetTabsAndFragments(String[] tabs, ArrayList<Fragment> fragments) {
        getView().setTab(fragments,tabs);
    }
}
