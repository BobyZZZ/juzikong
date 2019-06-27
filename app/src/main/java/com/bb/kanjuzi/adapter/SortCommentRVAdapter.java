package com.bb.kanjuzi.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.global.Constant;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortCommentRVAdapter extends BaseMultiItemQuickAdapter<SortDetailInfo.Comment, BaseViewHolder> {
    public SortCommentRVAdapter(@Nullable List<SortDetailInfo.Comment> data) {
        super(data);
        addItemType(Constant.THEME_DAY, R.layout.item_sort_comment);
        addItemType(Constant.THEME_NIGHT, R.layout.item_sort_comment_night);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortDetailInfo.Comment item) {
        String article = item.getArticle();
        String content = item.getContent();
        String publisher = item.getFromWho();
        String hot = item.getHot();
        String writer = item.getWriter();

        helper.setText(R.id.item_article, TextUtils.isEmpty(article) ? "未知" : article);
        helper.setText(R.id.item_content, TextUtils.isEmpty(content) ? "评论内容为空？啥玩意！" : content);
        helper.setText(R.id.tv_writer, TextUtils.isEmpty(writer) ? "佚名" : writer);
        helper.setText(R.id.tv_hot, TextUtils.isEmpty(hot) ? "未知" : hot);
        helper.setText(R.id.tv_publisher, TextUtils.isEmpty(publisher) ? "未知" : publisher);
    }
}
