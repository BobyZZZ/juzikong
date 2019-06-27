package com.bb.kanjuzi.mvp.model;

import android.util.Log;

import com.bb.kanjuzi.bean.CollectionInfo;
import com.bb.kanjuzi.bean.DetailPageBean;
import com.bb.kanjuzi.global.App;
import com.bb.kanjuzi.greendao.DetailPageBeanDao;
import com.bb.kanjuzi.mvp.contract.AllLikedActivityConstract;
import com.bb.kanjuzi.mvp.presenter.AllLikedActivityPresenter;
import com.bb.kanjuzi.utils.ChineseCharToEnUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/27.
 */

public class AllLikedActivityModel implements AllLikedActivityConstract.Model {
    AllLikedActivityPresenter mPresenter;
    private DetailPageBeanDao mDao;

    public AllLikedActivityModel(AllLikedActivityPresenter allLikedActivityPresenter) {
        mPresenter = allLikedActivityPresenter;
    }

    @Override
    public void getAllCollections() {
        if (mDao == null) {
            mDao = App.getDaoSession().getDetailPageBeanDao();
        }

        List<DetailPageBean> list = mDao.queryBuilder().where(DetailPageBeanDao.Properties.Id.isNotNull())
                .orderAsc(DetailPageBeanDao.Properties.Article).list();
        for (DetailPageBean detailPageBean : list) {
            Log.e("获取所有收藏", "detailPageBean: " + detailPageBean);
        }
        ChineseCharToEnUtil util = new ChineseCharToEnUtil();
        ArrayList<CollectionInfo> group = new ArrayList<>();
        String tempArticle = "";
        String tempFirstLetter = "";
        for (int i = 0; i < list.size(); i++) {
            DetailPageBean pageBean = list.get(i);
            String article = pageBean.getArticle();
            String firstLetter = util.getFirstLetter(article);
            if (i == 0) {
                group.add(new CollectionInfo(true, article, firstLetter));
                group.add(new CollectionInfo(pageBean));
            } else {
                if (tempArticle.equals(article)) {
                    //如果作品相同，直接插入
                    group.add(new CollectionInfo(pageBean));
                } else {
                    if (tempFirstLetter.equals(firstLetter)) {
                        //作品不同，首字母相同
                        group.add(new CollectionInfo(true, article, ""));
                    } else {
                        //作品不同，首字母不同
                        group.add(new CollectionInfo(true, article, firstLetter));
                    }
                    group.add(new CollectionInfo(pageBean));
                }
            }
            tempArticle = article;
            tempFirstLetter = firstLetter;
        }

        mPresenter.onGetAllCollections(group);
    }
}
