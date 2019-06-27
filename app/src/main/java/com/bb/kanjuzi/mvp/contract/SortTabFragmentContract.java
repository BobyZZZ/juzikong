package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.HeaderPicture;
import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SortBeanItem;
import com.bb.kanjuzi.bean.SquareTheNewest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/19.
 */

public interface SortTabFragmentContract {
    interface View {

        void onLoadData(ArrayList<SortBeanItem> datas);

        void onLoadDataFail();

        int getDataCount();
    }

    interface Model {

        void loadData(int page);
    }
}
