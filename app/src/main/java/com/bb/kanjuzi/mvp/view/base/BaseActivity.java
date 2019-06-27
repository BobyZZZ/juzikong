package com.bb.kanjuzi.mvp.view.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

import butterknife.ButterKnife;

/**
 * Created by Boby on 2019/6/17.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //透明状态栏
        ScreenUtils.transparentAndCoverStatusBar(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//只设置这个的效果：状态栏下拉出现，过一会自动消失
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//只设置这个的效果：状态栏下拉出现，出现后不会消失

        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initData();
        initView();
        initListener();
        process();
    }

    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void process();

    protected abstract void initTheme(int type);

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTheme(SharePreferenceUtils.getInt(Constant.THEME,Constant.THEME_DAY));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void onClickLeft(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_menu:

                break;
        }
    }
}
