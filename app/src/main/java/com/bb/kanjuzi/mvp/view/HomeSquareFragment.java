package com.bb.kanjuzi.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.TabFragmentPagerAdapter;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.HomeFragmentContract;
import com.bb.kanjuzi.mvp.presenter.HomeSquareFragmentPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseFragment;
import com.bb.kanjuzi.utils.ResUtils;

import java.security.PublicKey;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/17.
 */

public class HomeSquareFragment extends BaseFragment<HomeSquareFragmentPresenter> implements HomeFragmentContract.View {
    public static final int TAB_SQUARE = 0;
    public static final int TAB_SORT = 10;
    public static final int TAB_FIND = 20;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public int getType() {
        return type;
    }

    private int type;
    private TabFragmentPagerAdapter mAdapter;

    public static HomeSquareFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        HomeSquareFragment fragment = new HomeSquareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        type = arguments.getInt("type", 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    public HomeSquareFragmentPresenter createPresenter() {
        return new HomeSquareFragmentPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.initData();
    }

    @Override
    protected void initView(View view) {
        mAdapter = new TabFragmentPagerAdapter(getContext(), getChildFragmentManager(), "adapter");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void setTab(ArrayList<Fragment> fragmentList, String[] titles) {
        mAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.THEME_DAY:
                //日间模式
                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.white));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.black_translate), ResUtils.getColor(R.color.black));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.colorAccent));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.tab_gray), ResUtils.getColor(R.color.white));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.red_bg));
                break;
        }
    }
}
