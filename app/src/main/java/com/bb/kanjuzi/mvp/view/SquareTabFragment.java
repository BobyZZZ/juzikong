package com.bb.kanjuzi.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.SquareTabRecyclerViewAdapter;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.SquareTabFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SquareTabFragmentPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseFragment;
import com.bb.kanjuzi.utils.GlideUtils;
import com.bb.kanjuzi.utils.ResUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/17.
 */

public class SquareTabFragment extends BaseFragment<SquareTabFragmentPresenter> implements SquareTabFragmentContract.View {
    public static final int SQUARE_NEWEST = 0;
    public static final int SQUARE_HOT_TODAY = 1;
    public static final int SQUARE_RECOMMEND = 2;
    public static final int SQUARE_POPULAR = 3;


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SquareTabRecyclerViewAdapter mAdapter;
    private ImageView mHeader_img;
    private TextView mHeader_tag;
    private int type;
    private View mHeaderView;

    public static SquareTabFragment newInstance(int type) {
        SquareTabFragment fragment = new SquareTabFragment();
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
    public SquareTabFragmentPresenter createPresenter() {
        return new SquareTabFragmentPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.initData();
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout.setRefreshing(true);

        mAdapter = new SquareTabRecyclerViewAdapter(null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//设置动画

        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_header, null);
        mAdapter.addHeaderView(mHeaderView);//添加头部
        mHeader_img = mHeaderView.findViewById(R.id.header_img);
        mHeader_tag = mHeaderView.findViewById(R.id.header_tag);
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
                SquareTheNewest.SentencesItem item = ((SquareTabRecyclerViewAdapter) adapter).getData().get(position);
                DetailPageBean it = new DetailPageBean(item.getSentencesId(), item.getArticle(), item.getWriter(), item.getContent());
                DetailPageActivity.startDetailPageActivity(SquareTabFragment.this.getContext(), it);
            }
        });

        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPageBean it = new DetailPageBean(mHeader_tag.getText().toString(), null, null, mHeader_tag.getText().toString());
                DetailPageActivity.startDetailPageActivity(SquareTabFragment.this.getContext(), it);
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void onLoadData(List<SquareTheNewest.SentencesItem> datas) {
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
    public void getPicturesFail() {
        Toast.makeText(getContext(), "获取图片失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadPictureSuccess(HeaderPicture headerPicture) {
        List<HeaderPicture.OneItem> items = headerPicture.getItems();
        GlideUtils.play(mHeader_img, items.get(type).getImgSrc());
        mHeader_tag.setText(items.get(type).getText());
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
                //设置下拉刷新圈圈颜色
                mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.white));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                //设置下拉刷新圈圈颜色
                mSwipeRefreshLayout.setColorSchemeResources(R.color.red_text_bg);
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                break;
        }
    }
}
