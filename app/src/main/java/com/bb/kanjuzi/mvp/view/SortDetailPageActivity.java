package com.bb.kanjuzi.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.OtherRVAdapter;
import com.bb.kanjuzi.adapter.SortCommentRVAdapter;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.SortDetailPageActivityConstant;
import com.bb.kanjuzi.mvp.presenter.SortDetailPageActivityPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.GlideUtils;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.utils.SharePreferenceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortDetailPageActivity extends BaseActivity<SortDetailPageActivityPresenter> implements SortDetailPageActivityConstant.View {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.iv_blur_picture)
    ImageView mImageView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.mask)
    View mMask;
    @BindView(R.id.view_status_bar)
    View statuBar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    private SortCommentRVAdapter mAdapter;
    private SortDetailInfo.Other mItem;

    public static void startSortDetailPagerActivity(Context context, SortDetailInfo.Other other) {
        Intent intent = new Intent(context, SortDetailPageActivity.class);
        intent.putExtra("item", other);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_detail_page;
    }

    @Override
    protected SortDetailPageActivityPresenter createPresenter() {
        return new SortDetailPageActivityPresenter();
    }

    @Override
    protected void initData() {
        mItem = getIntent().getParcelableExtra("item");
        mPresenter.initData();
    }

    @Override
    protected void initView() {
        int statusBarHeight = ScreenUtils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = statuBar.getLayoutParams();
        lp.height = statusBarHeight;
        statuBar.setLayoutParams(lp);

        mTvTitle.setText(mItem.getName());

        mMask.setVisibility(View.VISIBLE);

        mAdapter = new SortCommentRVAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SortDetailInfo.Comment comment = ((SortCommentRVAdapter) adapter).getData().get(position);
                DetailPageBean detailPageBean = new DetailPageBean(comment.getId(),comment.getArticle(), comment.getWriter(), comment.getContent());
                DetailPageActivity.startDetailPageActivity(SortDetailPageActivity.this, detailPageBean);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(mItem.getHref());
            }
        }, mRecyclerView);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset:完全打开时为0,收起时为-appBarLayout.getTotalScrollRange()
                mTvTitle.setAlpha(-verticalOffset * 1.0f / appBarLayout.getTotalScrollRange());
            }
        });
    }

    @Override
    protected void process() {
        if (mItem != null) {
            mPresenter.loadData(mItem.getHref());
        }
    }

    @Override
    public void onLoadDataSuccess(SortDetailInfo sortDetailInfo) {
        List<SortDetailInfo.Comment> comments = sortDetailInfo.getComments();
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            //第一次加载
            GlideUtils.blur(mImageView, "http://" + sortDetailInfo.getImageUrl(), 15, new Runnable() {
                @Override
                public void run() {
                    AlphaAnimation alpha = new AlphaAnimation(1f, 0f);
                    alpha.setDuration(200);
                    alpha.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mMask.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    alpha.start();
                    mMask.clearAnimation();
                    mMask.setAnimation(alpha);
                }
            });
            mAdapter.setNewData(comments);//设置评论数据
            mAdapter.setNotDoAnimationCount(3);
            addHeaderView(sortDetailInfo);//添加头部，用于展示简介和相关作品
        } else {
            mAdapter.addData(comments);
            mMask.setVisibility(View.GONE);
        }

        //根据评论条数判断是否还有更多评论可拉取
        if (comments.size() < Constant.COMMENT_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadDataError(Throwable e) {
        mAdapter.loadMoreEnd();
        mMask.setVisibility(View.GONE);
    }

    @Override
    public List<SortDetailInfo.Comment> getComments() {
        List<SortDetailInfo.Comment> comments = mAdapter.getData();
        return comments;
    }

    @Override
    public int getCommentCount() {
        List<SortDetailInfo.Comment> comments = getComments();
        return comments == null ? 0 : comments.size();
    }

    @Override
    public void addHeaderView(SortDetailInfo sortDetailInfo) {
        View headerView;
        if (SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY) == Constant.THEME_DAY) {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info, null);
        } else {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info_night, null);
        }

        mAdapter.removeAllHeaderView();
        mAdapter.addHeaderView(headerView);
        TextView mTvJianJie = headerView.findViewById(R.id.tv_jianjie);
        RecyclerView mRvOther = headerView.findViewById(R.id.rv_other);
        View noOthers = headerView.findViewById(R.id.tv_no_others_tips);

        mTvJianJie.setText(sortDetailInfo.getJianJie());
        List<SortDetailInfo.Other> others = sortDetailInfo.getOthers();
        if (others != null && others.size() > 0) {
            noOthers.setVisibility(View.GONE);
            mRvOther.setVisibility(View.VISIBLE);

            OtherRVAdapter adapter = new OtherRVAdapter(R.layout.item_other, others);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvOther.setLayoutManager(layoutManager);
            mRvOther.setAdapter(adapter);

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_other_name) {
                        SortDetailInfo.Other other = (SortDetailInfo.Other) adapter.getData().get(position);
                        SortDetailPageActivity.startSortDetailPagerActivity(SortDetailPageActivity.this, other);
                    }
                }
            });
        } else {
            noOthers.setVisibility(View.VISIBLE);
            mRvOther.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mTvTitle.setTextColor(ResUtils.getColor(R.color.black));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.white));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mTvTitle.setTextColor(ResUtils.getColor(R.color.white));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                break;
        }
    }
}
