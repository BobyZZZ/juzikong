package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.SearchInfo;
import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.mvp.contract.SearchResultActivityContract;
import com.bb.kanjuzi.mvp.presenter.SearchResultActivityPresenter;
import com.bb.kanjuzi.net.NetHelper;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Boby on 2019/6/20.
 */

public class SearchResultActivityModel implements SearchResultActivityContract.Model {
    SearchResultActivityPresenter mPresenter;

    public SearchResultActivityModel(SearchResultActivityPresenter searchResultActivityPresenter) {
        this.mPresenter = searchResultActivityPresenter;
    }

    @Override
    public void search(String key, int page) {
        Observable<SearchInfo> observable = NetHelper.getSortApi().getSearchPage(key,page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchInfo searchInfo) {
                        mPresenter.onSearchSuccess(searchInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.getView().onSearchError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
