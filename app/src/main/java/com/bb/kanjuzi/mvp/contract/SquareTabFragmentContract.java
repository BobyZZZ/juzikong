package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SquareTheNewest;

import java.util.ArrayList;
import java.util.List;

/**
 * 广场item：最新上传、今日热门、今日推荐、最受欢迎
 */

public interface SquareTabFragmentContract {
    interface View {
        void onLoadData(List<SquareTheNewest.SentencesItem> datas);

        int getDataCount();

        void onLoadDataFail();

        void getPicturesFail();

        void onLoadPictureSuccess(HeaderPicture headerPicture);
    }

    interface Model {
        void loadData(int page);
    }
}
