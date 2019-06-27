package com.bb.kanjuzi.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.adapter.OtherRVAdapter;
import com.bb.kanjuzi.adapter.SearchCommentRVAdapter;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.bean.SearchInfo;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.SearchResultActivityContract;
import com.bb.kanjuzi.mvp.presenter.SearchResultActivityPresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.GlideUtils;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.utils.SharePreferenceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/21.
 */

public class SearchResultActivity extends BaseActivity<SearchResultActivityPresenter> implements SearchResultActivityContract.View {
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

    private String mKey;
    private SearchCommentRVAdapter mAdapter;

    public static void startSearchResultActivityForResult(Fragment fragment, String key) {
        Intent intent = new Intent(fragment.getContext(), SearchResultActivity.class);
        intent.putExtra("key", key);
        fragment.startActivityForResult(intent, 0);
    }

    public static void startSearchResultActivity(Context context, String key) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_detail_page;
    }

    @Override
    protected SearchResultActivityPresenter createPresenter() {
        return new SearchResultActivityPresenter();
    }

    @Override
    protected void initData() {
        mKey = getIntent().getStringExtra("key");
        mPresenter.init();
    }

    @Override
    protected void initView() {
        statuBar.getLayoutParams().height = ScreenUtils.getStatusBarHeight(this);
        mTvTitle.setText(mKey);

        mMask.setVisibility(View.VISIBLE);

        mAdapter = new SearchCommentRVAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreComment();
            }
        }, mRecyclerView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchInfo.Comment comment = ((SearchCommentRVAdapter) adapter).getData().get(position);
                DetailPageActivity.startDetailPageActivity(SearchResultActivity.this, new DetailPageBean(comment.getId(), comment.getArticle(), comment.getWriter(), comment.getContent()));
            }
        });
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
        mPresenter.search();
    }

    @Override
    public String getSearchKey() {
        return mKey;
    }

    @Override
    public void onSearchError(Throwable e) {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void onSearchSuccess(SearchInfo searchInfo) {
        Log.e("onSearchError", "onSearchSuccess: " + searchInfo);
        List<SearchInfo.Comment> comments = searchInfo.getComments();
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            //第一次加载
            String imageUrl = searchInfo.getImageUrl();
            if (TextUtils.isEmpty(imageUrl)) {
                //没有图片就不用加载图片
                mMask.setVisibility(View.GONE);
                //根据主题设置颜色
                int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
                mImageView.setBackgroundColor(type == Constant.THEME_DAY ? ResUtils.getColor(R.color.colorPrimary) : ResUtils.getColor(R.color.status_bar_night));
                ;
            } else {
                GlideUtils.blur(mImageView, "http://" + searchInfo.getImageUrl(), 15, new Runnable() {
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
            }
            mAdapter.setNewData(comments);//设置评论数据
            mAdapter.setNotDoAnimationCount(3);
            addHeaderView(searchInfo);//添加头部，用于展示简介和相关作品
        } else {
            mAdapter.addData(comments);
            mMask.setVisibility(View.GONE);
        }

        //根据评论条数判断是否还有更多评论可拉取
        if (comments != null && comments.size() < Constant.COMMENT_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    public void addHeaderView(SearchInfo searchInfo) {
        View headerView;
        if (SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY) == Constant.THEME_DAY) {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info, null);
        } else {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info_night, null);
        }

        mAdapter.removeAllHeaderView();
        mAdapter.addHeaderView(headerView);
        TextView mTvJianJie = headerView.findViewById(R.id.tv_jianjie);
        TextView mTvTagJianJie = headerView.findViewById(R.id.tv_tag_jianjie);
        RecyclerView mRvOtherWriter = headerView.findViewById(R.id.rv_other_writer);
        View noWriterText = headerView.findViewById(R.id.tv_no_others_tips_writer);

        RecyclerView mRvOther = headerView.findViewById(R.id.rv_other);
        View noOthers = headerView.findViewById(R.id.tv_no_others_tips);

        if (TextUtils.isEmpty(searchInfo.getJianJie())) {
            mTvTagJianJie.setVisibility(View.GONE);
            mTvJianJie.setVisibility(View.GONE);
        } else {
            mTvJianJie.setVisibility(View.VISIBLE);
            mTvJianJie.setText(searchInfo.getJianJie());//简介
        }

        //相关作品
        List<SearchInfo.RelativeArticle> relativeArticles = searchInfo.getRelativeArticles();
        if (relativeArticles != null && relativeArticles.size() > 0) {
            noOthers.setVisibility(View.GONE);
            mRvOther.setVisibility(View.VISIBLE);

            OtherRVAdapter adapter = new OtherRVAdapter(R.layout.item_other, relativeArticles);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvOther.setLayoutManager(layoutManager);
            mRvOther.setAdapter(adapter);

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_other_name) {
                        SearchInfo.RelativeArticle article = (SearchInfo.RelativeArticle) adapter.getData().get(position);
                        SearchResultActivity.startSearchResultActivity(SearchResultActivity.this, article.getName());
                    }
                }
            });
        } else {
            noOthers.setVisibility(View.VISIBLE);
            mRvOther.setVisibility(View.GONE);
        }
        //相关作者
        List<SearchInfo.RelativeWriter> relativeWriters = searchInfo.getRelativeWriters();
        if (relativeWriters != null && relativeWriters.size() > 0) {
            noWriterText.setVisibility(View.GONE);
            mRvOtherWriter.setVisibility(View.VISIBLE);

            OtherRVAdapter adapter = new OtherRVAdapter(R.layout.item_other, relativeWriters);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvOtherWriter.setLayoutManager(layoutManager);
            mRvOtherWriter.setAdapter(adapter);

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_other_name) {
                        SearchInfo.RelativeWriter writer = (SearchInfo.RelativeWriter) adapter.getData().get(position);
                        SearchResultActivity.startSearchResultActivity(SearchResultActivity.this, writer.getName());
                    }
                }
            });
        } else {
            noWriterText.setVisibility(View.VISIBLE);
            mRvOtherWriter.setVisibility(View.GONE);
        }
    }

    @Override
    public int getCommentCount() {
        List<SearchInfo.Comment> data = mAdapter.getData();
        return data == null ? 0 : data.size();
    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mTvTitle.setTextColor(ResUtils.getColor(R.color.black));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.white));
                mMask.setBackgroundColor(ResUtils.getColor(R.color.white));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mTvTitle.setTextColor(ResUtils.getColor(R.color.white));
                mRecyclerView.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                mMask.setBackgroundColor(ResUtils.getColor(R.color.background_night));
                break;
        }
    }
}
