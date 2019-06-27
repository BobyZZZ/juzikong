package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.SearchHistory;

import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public interface SearchFragmentContract {
    interface View {
        String getKey();

        void onLoadHistory(List<SearchHistory> histories);
    }

    interface Model {
        void getHistory();

        void recordHistory(String key);

        void cleanHistory();
    }
}
