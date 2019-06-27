package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.mvp.contract.SortDetailPageActivityConstant;
import com.bb.kanjuzi.mvp.presenter.SortDetailPageActivityPresenter;
import com.bb.kanjuzi.net.NetHelper;
import com.bb.kanjuzi.net.SortApi;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortDetailPageActivityModel implements SortDetailPageActivityConstant.Model {
    SortDetailPageActivityPresenter mPresenter;

    public SortDetailPageActivityModel(SortDetailPageActivityPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadData(String href, int page) {
        SortApi api = NetHelper.getSortApi();
        Observable<SortDetailInfo> observable = api.getSortDetailInfo(href,page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SortDetailInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SortDetailInfo sortDetailInfo) {
                        if (sortDetailInfo != null) {
                            Log.e("sortDetail", "onNext: " + sortDetailInfo);
                            mPresenter.onLoadDataSuccess(sortDetailInfo);
                        } else {
                            mPresenter.onLoadDataError(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPresenter.onLoadDataError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
