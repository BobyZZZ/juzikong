package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.SearchHistory;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.greendao.DaoSession;
import com.bb.kanjuzi.greendao.SearchHistoryDao;
import com.bb.kanjuzi.mvp.contract.SearchFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SearchFragmentPresenter;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public class SearchFragmentModel implements SearchFragmentContract.Model {
    SearchFragmentPresenter mPresenter;
    private SearchHistoryDao mDao;

    public SearchFragmentModel(SearchFragmentPresenter searchFragmentPresenter) {
        this.mPresenter = searchFragmentPresenter;
    }

    @Override
    public void getHistory() {
        if (mDao == null) {
            DaoSession session = App.getDaoSession();
            mDao = session.getSearchHistoryDao();
        }
        List<SearchHistory> histories = mDao.loadAll();
        mPresenter.onLoadHistory(histories);
    }

    @Override
    public void recordHistory(String name) {
        if (mDao == null) {
            mDao = App.getDaoSession().getSearchHistoryDao();
        }
        List<SearchHistory> result = mDao.queryBuilder().where(SearchHistoryDao.Properties.Name.eq(name)).list();
        if (result.size() > 0) {
            SearchHistory history = result.get(0);
            mDao.delete(history);
        }
        long insert = mDao.insert(new SearchHistory(name, null));
    }

    @Override
    public void cleanHistory() {
        if (mDao == null) {
            mDao = App.getDaoSession().getSearchHistoryDao();
        }
        mDao.deleteAll();
        mPresenter.onDeleteAll();
    }
}
