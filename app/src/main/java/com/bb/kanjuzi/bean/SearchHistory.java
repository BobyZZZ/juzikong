package com.bb.kanjuzi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Boby on 2019/6/25.
 */

@Entity
public class SearchHistory {
    @Id
    private String name;
    private String href;

    @Generated(hash = 1345946882)
    public SearchHistory(String name, String href) {
        this.name = name;
        this.href = href;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
