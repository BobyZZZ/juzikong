package com.bb.kanjuzi.mvp.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.TabFragmentPagerAdapter;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.MainActivityContract;
import com.bb.kanjuzi.mvp.presenter.MainActivityPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContract.View {
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.main_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.main_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.tv_title)
    TextView mtvTitle;
    @BindView(R.id.tv_back)
    TextView mtvBack;
    @BindView(R.id.tv_menu)
    TextView mtvMenu;
    @BindView(R.id.view_status_bar)
    View mStatus;

    TabFragmentPagerAdapter mPagerAdapter;
    private TextView mTvLikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.initTabsAndFragments();
    }

    @Override
    protected void initView() {
        mStatus.getLayoutParams().height = ScreenUtils.getStatusBarHeight(this);
        //actionBar
        mtvBack.setVisibility(View.GONE);
        mtvMenu.setVisibility(View.VISIBLE);
        mtvTitle.setText(R.string.app_name);
        mtvTitle.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        mtvTitle.setTextColor(ResUtils.getColor(R.color.colorAccent));

        //viewPager + tablayout
        mPagerAdapter = new TabFragmentPagerAdapter(this, getSupportFragmentManager(), "外层ViewPager的adapter");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        View NavHeader = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mTvLikeCount = NavHeader.findViewById(R.id.tv_like_count);
    }

    @Override
    protected void initListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_like:
                        Intent intent = new Intent(MainActivity.this, AllLikedActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_app_theme:
                        Intent intent2 = new Intent(MainActivity.this, AppThemeActivity.class);
                        startActivity(intent2);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                mPresenter.getAllLikeCount();
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.black));
                //设置图标
                Drawable drawable = getDrawable(R.drawable.nav_icon);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mtvMenu.setCompoundDrawables(drawable, null, null, null);

                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.white));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.black_translate), ResUtils.getColor(R.color.black));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.colorAccent));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.white));
                //设置图标
                Drawable drawable2 = getDrawable(R.drawable.nav_icon_white);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                mtvMenu.setCompoundDrawables(drawable2, null, null, null);

                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.tab_gray), ResUtils.getColor(R.color.white));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.red_bg));
                break;
        }
    }

    @Override
    public void setNavLikeCount(int size) {
        mTvLikeCount.setText("全部收藏：" + size);
    }

    @Override
    public void setSelectPage(int item) {

    }

    @Override
    public void setTab(ArrayList<Fragment> fragmentList, String[] titles) {
        mPagerAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void showUpdateDialog(Bundle bundle) {

    }

    @Override
    public void refreshView(int collectSize) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    protected void onClickLeft(View view) {
        switch (view.getId()) {
            case R.id.tv_menu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }
}
