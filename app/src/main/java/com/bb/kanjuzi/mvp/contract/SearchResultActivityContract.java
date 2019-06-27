package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.SearchInfo;
import com.bb.kanjuzi.bean.SortDetailInfo;

/**
 * Created by Boby on 2019/6/20.
 */

public interface SearchResultActivityContract {
    interface View {

        String getSearchKey();

        void onSearchError(Throwable e);

        void onSearchSuccess(SearchInfo searchInfo);

        void addHeaderView(SearchInfo searchInfo);

        int getCommentCount();
    }

    interface Model {

        void search(String key,int page);
    }
}
