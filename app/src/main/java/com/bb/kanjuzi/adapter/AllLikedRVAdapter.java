package com.bb.kanjuzi.adapter;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.CollectionInfo;
import com.bb.kanjuzi.utils.TEXTUtils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Boby on 2019/6/27.
 */

public class AllLikedRVAdapter extends BaseSectionQuickAdapter<CollectionInfo, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public AllLikedRVAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, CollectionInfo item) {
        helper.setText(R.id.collection_group_first_word, item.getFirstWord());
        helper.setText(R.id.collection_group_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionInfo item) {
        helper.setText(R.id.item_writer, item.t.getWriter());
        String content = TEXTUtils.huanHang(item.t.getContent());

        helper.setText(R.id.item_content, content);
    }
}
