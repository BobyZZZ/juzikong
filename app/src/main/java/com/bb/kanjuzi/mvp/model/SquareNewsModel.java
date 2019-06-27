package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.mvp.contract.SquareTabFragmentContract;
import com.bb.kanjuzi.mvp.presenter.SquareTabFragmentPresenter;
import com.bb.kanjuzi.mvp.view.SquareTabFragment;
import com.bb.kanjuzi.net.HeaderPictureApi;
import com.bb.kanjuzi.net.NetHelper;
import com.bb.kanjuzi.net.SquareApi;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Boby on 2019/6/18.
 */

public class SquareNewsModel implements SquareTabFragmentContract.Model {
    String TAG = getClass().getSimpleName();
    private SquareTabFragmentPresenter mPresenter;
    private int type;
    private Object mApi;

    public SquareNewsModel(SquareTabFragmentPresenter presenter) {
        mPresenter = presenter;
        this.type = presenter.getView().getType();
    }

    @Override
    public void loadData(int page) {
        SquareApi squareApi = NetHelper.getSquareApi();
        Observable observable = null;
        switch (type) {
            case SquareTabFragment.SQUARE_NEWEST:
                observable = squareApi.getSquareNews(page);
                break;
            case SquareTabFragment.SQUARE_HOT_TODAY:
                observable = squareApi.getSquareHotToday(page);
                break;
            case SquareTabFragment.SQUARE_RECOMMEND:
                observable = squareApi.getSquareRecommend(page);
                break;
            case SquareTabFragment.SQUARE_POPULAR:
                observable = squareApi.getSquarePopular(page);
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (observable != null) {
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<SquareTheNewest>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                            mPresenter.onLoadDataFail();
                        }

                        @Override
                        public void onNext(SquareTheNewest squareTheNewest) {
                            Log.e(TAG, "onNext: " + squareTheNewest);
                            if (squareTheNewest == null) {
                                mPresenter.onLoadDataFail();
                            } else {
                                mPresenter.onLoadDataSuccess(squareTheNewest);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getPictures() {
        HeaderPictureApi pictureApi = NetHelper.getHeaderApi();
        Observable<HeaderPicture> observable = pictureApi.getPictures();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HeaderPicture>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HeaderPicture headerPicture) {
                        if (headerPicture == null) {
                            mPresenter.getPicturesFail();
                        } else {
                            Log.e(TAG, "onNext: " + headerPicture);
                            mPresenter.onLoadPictureSuccess(headerPicture);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.getPicturesFail();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
