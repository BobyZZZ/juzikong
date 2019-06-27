package com.bb.kanjuzi.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Boby on 2019/6/27.
 */

public class CollectionInfo extends SectionEntity<DetailPageBean> {
    private String mFirstWord;

    public CollectionInfo(boolean isHeader, String header, String firstWord) {
        super(isHeader, header);
        mFirstWord = firstWord;
    }

    public CollectionInfo(DetailPageBean detailPageBean) {
        super(detailPageBean);
    }

    public String getFirstWord() {
        return mFirstWord;
    }

    public void setFirstWord(String firstWord) {
        mFirstWord = firstWord;
    }
}
