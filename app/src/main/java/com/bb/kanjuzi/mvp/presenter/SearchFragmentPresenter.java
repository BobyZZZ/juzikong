package com.bb.kanjuzi.mvp.presenter;

import com.bb.kanjuzi.bean.SearchHistory;
import com.bb.kanjuzi.mvp.model.SearchFragmentModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SearchFragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public class SearchFragmentPresenter extends BasePresenter<SearchFragment> {

    private SearchFragmentModel mModel;

    public void init() {
        mModel = new SearchFragmentModel(this);
    }

    public void getHistory() {
        mModel.getHistory();
    }

    public void onLoadHistory(List<SearchHistory> histories) {
        SearchFragment view = getView();
        if (view != null) {
            Collections.reverse(histories);
            view.onLoadHistory(histories);
        }
    }

    public void record(String history) {
        mModel.recordHistory(history);
    }

    public void cleanHistory() {
        mModel.cleanHistory();
    }

    public void onDeleteAll() {
        SearchFragment view = getView();
        if (view != null) {
            view.onLoadHistory(null);
        }
    }
}
