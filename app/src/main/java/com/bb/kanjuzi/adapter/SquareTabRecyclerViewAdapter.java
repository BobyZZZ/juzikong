package com.bb.kanjuzi.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.utils.TEXTUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Boby on 2019/6/17.
 */

public class SquareTabRecyclerViewAdapter extends BaseMultiItemQuickAdapter<SquareTheNewest.SentencesItem, BaseViewHolder> {
    public SquareTabRecyclerViewAdapter(@Nullable List<SquareTheNewest.SentencesItem> datas) {
        super(datas);
        addItemType(Constant.THEME_DAY, R.layout.item_square_tab_recycler_view);
        addItemType(Constant.THEME_NIGHT, R.layout.item_square_tab_recycler_view_night);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareTheNewest.SentencesItem item) {
        String article = item.getArticle();
        String writer = item.getWriter();
        String content = TEXTUtils.huanHang(item.getContent());

        helper.setText(R.id.item_article, TextUtils.isEmpty(article) ? "未知" : article);
        helper.setText(R.id.item_writer, TextUtils.isEmpty(writer) ? "佚名" : writer);
        helper.setText(R.id.item_content, TextUtils.isEmpty(content) ? "啥玩意，怎么为空呢" : content);
    }
}
