package com.bb.kanjuzi.mvp.presenter;

import com.bb.kanjuzi.bean.CollectionInfo;
import com.bb.kanjuzi.mvp.model.AllLikedActivityModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.AllLikedActivity;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/27.
 */

public class AllLikedActivityPresenter extends BasePresenter<AllLikedActivity> {

    private AllLikedActivityModel mModel;

    public void init() {
        mModel = new AllLikedActivityModel(this);
    }

    public void getAllCollections() {
        mModel.getAllCollections();
    }

    public void onGetAllCollections(ArrayList<CollectionInfo> group) {
        AllLikedActivity view = getView();
        if (view != null) {
            view.showCollections(group);
        }
    }
}
