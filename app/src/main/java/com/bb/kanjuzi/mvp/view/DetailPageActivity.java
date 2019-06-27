package com.bb.kanjuzi.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.greendao.DetailPageBeanDao;
import com.bb.kanjuzi.mvp.presenter.DetailPagePresenter;
import com.bb.kanjuzi.mvp.view.base.BaseActivity;
import com.bb.kanjuzi.utils.ClipboardUtils;
import com.bb.kanjuzi.utils.GlideUtils;
import com.bb.kanjuzi.utils.ResUtils;
import com.bb.kanjuzi.utils.ScreenUtils;
import com.bb.kanjuzi.view.Dialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Boby on 2019/6/19.
 */

public class DetailPageActivity extends BaseActivity<DetailPagePresenter> {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.content_bg)
    ImageView mContentBg;
    @BindView(R.id.iv_like)
    ImageView mIvLike;
    @BindView(R.id.item_article)
    TextView mArticle;
    @BindView(R.id.item_writer)
    TextView mWriter;
    @BindView(R.id.item_content)
    TextView mContent;
    @BindView(R.id.tv_title)
    TextView mtvTitle;
    @BindView(R.id.tv_back)
    TextView mBack;
    @BindView(R.id.view_status_bar)
    View mStatus;

    private boolean like;
    private DetailPageBean mItem;
    private long lastClick;
    private DetailPageBeanDao mDao;

    public static void startDetailPageActivity(Context context, DetailPageBean detailPageBean) {
        Intent intent = new Intent(context, DetailPageActivity.class);
        intent.putExtra("item", detailPageBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_page;
    }

    @Override
    protected DetailPagePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        mItem = getIntent().getParcelableExtra("item");
        List<DetailPageBean> list = App.getDaoSession().getDetailPageBeanDao().queryBuilder().where(DetailPageBeanDao.Properties.Id.eq(mItem.getId())).list();
        mItem.setLiked(list.size() > 0);
    }

    @Override
    protected void initView() {
        mStatus.getLayoutParams().height = ScreenUtils.getStatusBarHeight(this);
        mtvTitle.setText("卡片");
    }

    @Override
    protected void initListener() {
        mIvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItem.setLiked(!mItem.getLiked());
                if (mItem.getLiked()) {
                    //动画
                    scaleAnimation(mIvLike);
                    insertIntoDb(mItem);
                } else {
                    removeFromDb(mItem);
                }
                mIvLike.setBackgroundResource(mItem.getLiked() ? R.drawable.collection : R.drawable.no_collection);
            }
        });
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long stamp = System.currentTimeMillis();
                if ((stamp - lastClick) < 200) {
                    ClipboardUtils.setClipboard(mContent.getText().toString());
                    Toast.makeText(DetailPageActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
//                    Dialog dialog = new Dialog();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title","这是标题");
//                    bundle.putString("message","这是内容");
//                    dialog.setArguments(bundle);
//                    dialog.show(getSupportFragmentManager(),"tag");
                } else {
                    Toast.makeText(DetailPageActivity.this, "双击复制内容", Toast.LENGTH_SHORT).show();
                }
                lastClick = stamp;
            }
        });
    }

    private void removeFromDb(DetailPageBean item) {
        if (mDao == null) {
            mDao = App.getDaoSession().getDetailPageBeanDao();
        }
        mDao.delete(item);
    }

    private void insertIntoDb(DetailPageBean item) {
        if (mDao == null) {
            mDao = App.getDaoSession().getDetailPageBeanDao();
        }
//        List<DetailPageBean> list = mDao.queryBuilder().where(DetailPageBeanDao.Properties.Id.eq(item.getId())).list();
        mDao.insert(item);
    }

    private void showBottomSheetDialog() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(R.layout.item_sort_comment, getData()));
        sheetDialog.setContentView(recyclerView);
        sheetDialog.show();
    }

    List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "条数据");
        }
        return list;
    }

    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_content, item);
        }
    }

    private void scaleAnimation(final View target) {
        ScaleAnimation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(200);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                target.setBackgroundResource(R.drawable.collection);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        target.startAnimation(animation);
    }

    @Override
    protected void process() {
        //设置模糊图片、设置上边圆角图片
        GlideUtils.blur(mIvBg, R.drawable.card_default_bg, 25);
        GlideUtils.round(mContentBg, R.drawable.card_default_bg, 16, true, true, false, false);

        if (mItem != null) {
            like = mItem.isLiked();
            String article = mItem.getArticle();
            String writer = mItem.getWriter();
            String content = mItem.getContent();
            mArticle.setText(TextUtils.isEmpty(article) ? "未知" : article);
            mWriter.setText(TextUtils.isEmpty(writer) ? "佚名" : writer);
            mContent.setText(TextUtils.isEmpty(content) ? "啥玩意？怎么为空？" : content);
            mIvLike.setBackgroundResource(like ? R.drawable.collection : R.drawable.no_collection);
        }
    }

    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.black));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.white));
                break;
        }
    }
}
