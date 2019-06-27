package com.bb.kanjuzi.mvp.presenter;

import android.util.Log;

import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.model.SortDetailPageActivityModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SortDetailPageActivity;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortDetailPageActivityPresenter extends BasePresenter<SortDetailPageActivity> {

    private SortDetailPageActivityModel mModel;

    public void initData() {
        mModel = new SortDetailPageActivityModel(this);
    }

    public void loadData(String href) {
        SortDetailPageActivity view = getView();
        int page = view.getCommentCount() / Constant.COMMENT_PAGE_SIZE;
        Log.e(TAG, "loadData: page:" + page);
        mModel.loadData(href, page);
    }

    public void onLoadDataError(Throwable e) {
        SortDetailPageActivity view = getView();
        if (view != null) {
            view.onLoadDataError(e);
        }
    }

    public void onLoadDataSuccess(SortDetailInfo sortDetailInfo) {
        SortDetailPageActivity view = getView();
        if (view != null) {
            int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
            for (SortDetailInfo.Comment comment : sortDetailInfo.getComments()) {
                comment.setItemType(type);
            }
            view.onLoadDataSuccess(sortDetailInfo);
        }
    }
}
