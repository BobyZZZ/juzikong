package com.bb.kanjuzi.mvp.presenter.base;

/**
 * Created by Boby on 2019/6/17.
 */

public class BasePresenter<V> {
    protected String TAG;
    public BasePresenter() {
        TAG = getClass().getSimpleName();
    }
    private V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    public boolean isAttachView() {
        return view != null;
    }
}
