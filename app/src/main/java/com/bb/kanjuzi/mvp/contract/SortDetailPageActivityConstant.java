package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.SortDetailInfo;

import java.util.List;

/**
 * Created by Boby on 2019/6/19.
 */

public interface SortDetailPageActivityConstant {
    interface View {

        void onLoadDataSuccess(SortDetailInfo sortDetailInfo);

        void onLoadDataError(Throwable e);

        List<SortDetailInfo.Comment> getComments();
        int getCommentCount();

        void addHeaderView(SortDetailInfo sortDetailInfo);
    }

    interface Model {

        void loadData(String name,int page);
    }
}
