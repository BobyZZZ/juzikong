package com.bb.kanjuzi.mvp.model;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.mvp.contract.HomeFragmentContract;
import com.bb.kanjuzi.mvp.presenter.HomeSquareFragmentPresenter;
import com.bb.kanjuzi.mvp.view.HomeSquareFragment;
import com.bb.kanjuzi.mvp.view.SortTabFragment;
import com.bb.kanjuzi.mvp.view.SquareTabFragment;
import com.bb.kanjuzi.utils.ResUtils;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/19.
 */

public class HomeSquareModel implements HomeFragmentContract.Model {
    private int type;
    private String[] mTabs;
    private ArrayList<android.support.v4.app.Fragment> mFragments;
    HomeSquareFragmentPresenter mPresenter;

    public HomeSquareModel(HomeSquareFragmentPresenter presenter) {
        this.mPresenter = presenter;
        this.type = presenter.getView().getType();
    }

    @Override
    public void getTabsAndFragment() {
        switch (type) {
            case HomeSquareFragment.TAB_SQUARE:
                mTabs = ResUtils.getStringArray(R.array.hotpage_item);
                break;
            case HomeSquareFragment.TAB_SORT:
                mTabs = ResUtils.getStringArray(R.array.menu_item);
                break;
            case HomeSquareFragment.TAB_FIND:
                mTabs = ResUtils.getStringArray(R.array.menu_item);
                break;
        }

        if (type == HomeSquareFragment.TAB_SQUARE) {
            //广场
            mFragments = new ArrayList<>();
            for (int i = 0; i < mTabs.length; i++) {
                mFragments.add(SquareTabFragment.newInstance(type + i));
            }
            mPresenter.onGetTabsAndFragments(mTabs, mFragments);
        } else if (type == HomeSquareFragment.TAB_SORT) {
            //分类
            mFragments = new ArrayList<>();
            for (int i = 0; i < mTabs.length; i++) {
                mFragments.add(SortTabFragment.newInstance(type + i));
            }
            mPresenter.onGetTabsAndFragments(mTabs, mFragments);
        } else {
            //搜索
            mFragments = new ArrayList<>();
            for (int i = 0; i < mTabs.length; i++) {
                mFragments.add(SortTabFragment.newInstance(10 + i));
            }
            mPresenter.onGetTabsAndFragments(mTabs, mFragments);
        }
    }
}
