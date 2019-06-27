package com.bb.kanjuzi.mvp.contract;

import com.bb.kanjuzi.bean.CollectionInfo;

import java.util.ArrayList;

/**
 * Created by Boby on 2019/6/27.
 */

public interface AllLikedActivityConstract {
    interface View {

        void showCollections(ArrayList<CollectionInfo> group);
    }

    interface Model {

        void getAllCollections();
    }
}
