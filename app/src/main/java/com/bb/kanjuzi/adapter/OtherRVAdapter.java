package com.bb.kanjuzi.adapter;

import android.support.annotation.Nullable;

import com.bb.kanjuzi.R;
import com.bb.kanjuzi.bean.BaseArticle;
import com.bb.kanjuzi.global.Constant;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Boby on 2019/6/20.
 */

public class OtherRVAdapter<T extends BaseArticle> extends BaseQuickAdapter<T, BaseViewHolder> {
    public OtherRVAdapter(int layoutId, @Nullable List<T> data) {
        super(layoutId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        helper.setText(R.id.tv_other_name, item.getName());
        helper.addOnClickListener(R.id.tv_other_name);
    }
}
