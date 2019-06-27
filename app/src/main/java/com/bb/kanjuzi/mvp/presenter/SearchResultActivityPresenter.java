package com.bb.kanjuzi.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.bb.kanjuzi.bean.SearchInfo;
import com.bb.kanjuzi.bean.SquareTheNewest;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.model.SearchResultActivityModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SearchFragment;
import com.bb.kanjuzi.mvp.view.SearchResultActivity;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

/**
 * Created by Boby on 2019/6/20.
 */

public class SearchResultActivityPresenter extends BasePresenter<SearchResultActivity> {

    private SearchResultActivityModel mModel;

    public void init() {
        mModel = new SearchResultActivityModel(this);
    }

    public void search() {
        String key = getView().getSearchKey();
        if (!TextUtils.isEmpty(key)) {
            mModel.search(key, 0);
        }
    }

    public void loadMoreComment() {
        SearchResultActivity view = getView();
        if (view != null && !TextUtils.isEmpty(view.getSearchKey())) {
            mModel.search(view.getSearchKey(), view.getCommentCount() / Constant.COMMENT_PAGE_SIZE);
        }
    }

    public void onSearchSuccess(SearchInfo searchInfo) {
        SearchResultActivity view = getView();
        if (view != null) {
            int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
            for (SearchInfo.Comment comment : searchInfo.getComments()) {
                comment.setItemType(type);
            }
            view.onSearchSuccess(searchInfo);
        }
    }
}
