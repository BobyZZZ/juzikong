package com.bb.kanjuzi.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SortBeanItem;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.utils.GlideUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortTabRVAdapter extends BaseMultiItemQuickAdapter<SortBeanItem, BaseViewHolder> {

    public SortTabRVAdapter(Context context, @Nullable List<SortBeanItem> data) {
        super(data);
        this.mContext = context;
        addItemType(Constant.THEME_DAY, R.layout.item_sort_tab_recycler_view);
        addItemType(Constant.THEME_NIGHT, R.layout.item_sort_tab_recycler_view_night);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortBeanItem item) {
        SortBean.Text text = item.getText();
        String article = text.getArtical();
        String writer = text.getWriter();
        String content = text.getContent();

        helper.setText(R.id.item_article, TextUtils.isEmpty(article) ? "未知" : article);
        helper.setText(R.id.item_writer, TextUtils.isEmpty(writer) ? "佚名" : writer);
        helper.setText(R.id.item_content, TextUtils.isEmpty(content) ? "啥玩意，怎么为空呢" : content);

        ImageView view = helper.getView(R.id.iv_book);
        String url = item.getImageUrl();
        GlideUtils.play(view, "http://" + url);
    }
}
