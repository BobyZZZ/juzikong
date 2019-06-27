package com.bb.kanjuzi.mvp.view;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.greendao.DetailPageBeanDao;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/27.
 */

public class AppThemeActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    View mStatus;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.fl_bg_day)
    View mBgDay;
    @BindView(R.id.fl_bg_night)
    View mBgNight;
    @BindView(R.id.root_layout)
    View mRootView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_theme;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        mStatus.getLayoutParams().height = ScreenUtils.getStatusBarHeight(this);

        mTvTitle.setText(R.string.app_theme);
    }

    @Override
    protected void initListener() {
        mBgDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtils.putInt(Constant.THEME, Constant.THEME_DAY);
                initTheme(Constant.THEME_DAY);
            }
        });

        mBgNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtils.putInt(Constant.THEME, Constant.THEME_NIGHT);
                initTheme(Constant.THEME_NIGHT);
            }
        });
    }

    @Override
    protected void process() {

    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                mTvTitle.setTextColor(ResUtils.getColor(R.color.black));
                mRootView.setBackgroundColor(ResUtils.getColor(R.color.white));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mTvTitle.setTextColor(ResUtils.getColor(R.color.white));
                mRootView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                break;
        }
    }
}
