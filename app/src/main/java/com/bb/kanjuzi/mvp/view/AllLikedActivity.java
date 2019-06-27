package com.bb.kanjuzi.mvp.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.AllLikedRVAdapter;
import com.bb.kanjuzi.bean.CollectionInfo;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.AllLikedActivityConstract;
import com.bb.kanjuzi.mvp.presenter.AllLikedActivityPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.utils.SharePreferenceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/27.
 */

public class AllLikedActivity extends BaseActivity<AllLikedActivityPresenter> implements AllLikedActivityConstract.View {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.view_status_bar)
    View mStatus;
    private AllLikedRVAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_like;
    }

    @Override
    protected AllLikedActivityPresenter createPresenter() {
        return new AllLikedActivityPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.init();
    }

    @Override
    protected void initView() {
        mStatus.getLayoutParams().height = ScreenUtils.getStatusBarHeight(this);
        mTvTitle.setText(R.string.like);

        mSwipeRefreshLayout.setRefreshing(true);

        int theme = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
        if (theme == Constant.THEME_DAY) {
            mAdapter = new AllLikedRVAdapter(R.layout.collection_group_content_item, R.layout.collection_group_header_item, null);
        } else {
            mAdapter = new AllLikedRVAdapter(R.layout.collection_group_content_item_night, R.layout.collection_group_header_item_night, null);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAllCollections();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectionInfo collectionInfo = ((AllLikedRVAdapter) adapter).getData().get(position);
                if (!collectionInfo.isHeader) {
                    DetailPageActivity.startDetailPageActivity(AllLikedActivity.this, collectionInfo.t);
                }
            }
        });
    }

    @Override
    protected void process() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllCollections();
    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                mTvTitle.setTextColor(ResUtils.getColor(R.color.black));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.white));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mTvTitle.setTextColor(ResUtils.getColor(R.color.white));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                break;
        }
    }

    @Override
    public void showCollections(ArrayList<CollectionInfo> group) {
        mAdapter.setNewData(group);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
