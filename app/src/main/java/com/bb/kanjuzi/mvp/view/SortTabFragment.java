package com.bb.kanjuzi.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.SortTabRVAdapter;
import com.bb.kanjuzi.adapter.SquareTabRecyclerViewAdapter;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SortBeanItem;
import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.SortTabFragmentContract;
import com.bb.kanjuzi.mvp.contract.SquareTabFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SortTabFragmentPresenter;
import com.bb.kanjuzi.mvp.presenter.SquareTabFragmentPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseFragment;
import com.bb.kanjuzi.utils.GlideUtils;
import com.bb.kanjuzi.utils.ResUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/17.
 */

public class SortTabFragment extends BaseFragment<SortTabFragmentPresenter> implements SortTabFragmentContract.View {
    public static final int SORT_BOOK = 10;
    public static final int SORT_MOVIE = 11;
    public static final int SORT_SANWEN = 12;
    public static final int SORT_DONGMAN = 13;
    public static final int SORT_SOAP = 14;
    public static final int SORT_POETRY = 15;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SortTabRVAdapter mAdapter;
    private int type;

    public static SortTabFragment newInstance(int type) {
        SortTabFragment fragment = new SortTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.type = getArguments().getInt("type", 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_square_item;
    }

    @Override
    public SortTabFragmentPresenter createPresenter() {
        return new SortTabFragmentPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.initData();
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setRefreshing(true);

        mAdapter = new SortTabRVAdapter(getContext(), null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//设置动画
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.pullToRefresh(false);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.pullToRefresh(true);
            }
        }, mRecyclerView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SortBeanItem sortBeanItem = ((SortTabRVAdapter) adapter).getData().get(position);
                SortDetailInfo.Other other = new SortDetailInfo.Other(sortBeanItem.getText().getArtical(), sortBeanItem.getText().getHref());
                SortDetailPageActivity.startSortDetailPagerActivity(SortTabFragment.this.getContext(), other);
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void onLoadData(ArrayList<SortBeanItem> datas) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            //刷新，将数据重置
            mAdapter.setNewData(datas);
            mAdapter.setNotDoAnimationCount(3);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            //加载更多，将数据插入
            mAdapter.addData(datas);
        }
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadDataFail() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public int getDataCount() {
        return mAdapter == null ? 0 : mAdapter.getData().size();
    }

    public int getType() {
        return type;
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.THEME_DAY:
                //日间模式
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.white));
                //设置下拉刷新圈圈颜色
                mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                //设置下拉刷新圈圈颜色
                mSwipeRefreshLayout.setColorSchemeResources(R.color.red_text_bg);
                break;
        }
    }
}
