package com.bb.kanjuzi.mvp.presenter;

import android.util.Log;

import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SortBeanItem;
import com.bb.kanjuzi.global.Constant;
import com.bb.kanjuzi.mvp.model.SortTabModel;
import com.bb.kanjuzi.mvp.presenter.base.BasePresenter;
import com.bb.kanjuzi.mvp.view.SortTabFragment;
import com.bb.kanjuzi.utils.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortTabFragmentPresenter extends BasePresenter<SortTabFragment> {
    SortTabModel mModel;

    public void initData() {
        if (mModel == null) {
            mModel = new SortTabModel(this);
        }
    }

    public void onLoadDataFail() {
        SortTabFragment view = getView();
        if (view != null) {
            view.onLoadDataFail();
        }
    }

    public void onLoadDataSuccess(SortBean sortBean) {
        List<SortBean.Image> images = sortBean.getImages();
        List<SortBean.Text> texts = sortBean.getTexts();

        ArrayList<SortBeanItem> datas = new ArrayList<>();
        int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
        for (int i = 0; i < images.size(); i++) {
            SortBeanItem item = new SortBeanItem(images.get(i).getImageSrc(), texts.get(i));
            item.setItemType(type);
            datas.add(item);
        }

        SortTabFragment view = getView();
        if (view != null) {
            view.onLoadData(datas);
        }
    }

    public void pullToRefresh(boolean loadMore) {
        if (loadMore) {
            //加载更多
            int dataCount = getView().getDataCount();
            Log.e(TAG, "loadMoreComment: " + dataCount);
            mModel.loadData(dataCount / Constant.SORT_PAGE_SIZE);
        } else {
            Log.e(TAG, "pullToRefresh: ");
            mModel.loadData(0);
        }
    }

    public void process() {
        mModel.loadData(0);
    }
}
