package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.mvp.contract.SortTabFragmentContract;
import com.bb.kanjuzi.mvp.contract.SquareTabFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SortTabFragmentPresenter;
import com.bb.kanjuzi.mvp.presenter.SquareTabFragmentPresenter;
import com.bb.kanjuzi.mvp.view.SortTabFragment;
import com.bb.kanjuzi.mvp.view.SquareTabFragment;
import com.bb.kanjuzi.net.HeaderPictureApi;
import com.bb.kanjuzi.net.NetHelper;
import com.bb.kanjuzi.net.SortApi;
import com.bb.kanjuzi.net.SquareApi;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Boby on 2019/6/18.
 */

public class SortTabModel implements SortTabFragmentContract.Model {
    String TAG = getClass().getSimpleName();
    private SortTabFragmentPresenter mPresenter;
    private int type;

    public SortTabModel(SortTabFragmentPresenter presenter) {
        mPresenter = presenter;
        this.type = presenter.getView().getType();
    }

    @Override
    public void loadData(int page) {
        SortApi api = NetHelper.getSortApi();
        Observable<SortBean> observable = null;
        switch (type) {
            case SortTabFragment.SORT_BOOK:
                observable = api.getBooks(page);
                break;
            case SortTabFragment.SORT_DONGMAN:
                observable = api.getDongMans(page);
                break;
            case SortTabFragment.SORT_MOVIE:
                observable = api.getMovies(page);
                break;
            case SortTabFragment.SORT_POETRY:
                observable = api.getPoetys(page);
                break;
            case SortTabFragment.SORT_SANWEN:
                observable = api.getSanWens(page);
                break;
            case SortTabFragment.SORT_SOAP:
                observable = api.getSoaps(page);
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (observable != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<SortBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                            mPresenter.onLoadDataFail();
                        }

                        @Override
                        public void onNext(SortBean sortBean) {
                            Log.e(TAG, "onNext: " + sortBean);
                            if (sortBean == null) {
                                mPresenter.onLoadDataFail();
                            } else {
                                mPresenter.onLoadDataSuccess(sortBean);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
