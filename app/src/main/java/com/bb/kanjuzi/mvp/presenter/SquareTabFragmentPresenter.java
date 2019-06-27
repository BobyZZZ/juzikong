package com.bb.kanjuzi.mvp.presenter;

import android.util.Log;

import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.contract.SquareTabFragmentContract;
import com.bb.kanjuzi.mvp.model.SquareNewsModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SquareTabFragment;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

import java.util.List;

/**
 * Created by Boby on 2019/6/17.
 */

public class SquareTabFragmentPresenter extends BasePresenter<SquareTabFragment> {
    private SquareNewsModel mModel;

    public void initData() {
        if (mModel == null) {
            mModel = new SquareNewsModel(this);
        }
    }

    public void process() {
        mModel.loadData(0);
        mModel.getPictures();
    }

    public void onLoadDataFail() {
        SquareTabFragmentContract.View view = getView();
        if (view != null) {
            view.onLoadDataFail();
        }
    }

    public void onLoadDataSuccess(SquareTheNewest squareTheNewest) {
        List<SquareTheNewest.SentencesItem> mDatas = squareTheNewest.getSentencesItems();
        SquareTabFragmentContract.View view = getView();
        if (view != null) {
            int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
            for (SquareTheNewest.SentencesItem item : mDatas) {
                item.setItemUIType(type);
            }
            view.onLoadData(mDatas);
        }
    }

    public void pullToRefresh(boolean loadMore) {
        if (loadMore) {
            //加载更多
            int dataCount = getView().getDataCount();
            Log.e(TAG, "loadMoreComment: " + dataCount);
            mModel.loadData(dataCount / Constant.SQUARE_PAGE_SIZE);
        } else {
            Log.e(TAG, "pullToRefresh: ");
            mModel.loadData(0);
        }
    }

    public void getPicturesFail() {
        SquareTabFragmentContract.View view = getView();
        if (view != null) {
            view.getPicturesFail();
        }
    }

    public void onLoadPictureSuccess(HeaderPicture headerPicture) {
        SquareTabFragmentContract.View view = getView();
        if (view != null) {
            view.onLoadPictureSuccess(headerPicture);
        }
    }
}
