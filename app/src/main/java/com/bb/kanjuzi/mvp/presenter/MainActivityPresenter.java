package com.bb.kanjuzi.mvp.presenter;

import android.support.v4.app.Fragment;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.mvp.contract.MainActivityContract;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SearchFragment;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.mvp.view.HomeSquareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/17.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> {

    private String[] mTitles;
    private ArrayList<Fragment> mFragments;

    public void initTabsAndFragments() {
        mTitles = ResUtils.getStringArray(R.array.tab_item);
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            switch (i) {
                case 0:
                    mFragments.add(HomeSquareFragment.newInstance(HomeSquareFragment.TAB_SQUARE));
                    break;
                case 1:
                    mFragments.add(HomeSquareFragment.newInstance(HomeSquareFragment.TAB_SORT));
                    break;
                case 2:
                    mFragments.add(SearchFragment.newInstance());
                    break;
                default:
                    mFragments.add(HomeSquareFragment.newInstance(HomeSquareFragment.TAB_SQUARE));
            }
        }
    }

    public void process() {
        getView().setTab(mFragments, mTitles);
    }

    public void getAllLikeCount() {
        List<DetailPageBean> detailPageBeans = App.getDaoSession().getDetailPageBeanDao().loadAll();
        int size = detailPageBeans.size();
        MainActivityContract.View view = getView();
        if (view != null) {
            view.setNavLikeCount(size);
        }
    }
}
