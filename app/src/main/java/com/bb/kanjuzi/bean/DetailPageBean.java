package com.bb.kanjuzi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.SectionEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Boby on 2019/6/19.
 */

@Entity
public class DetailPageBean implements Parcelable {
    @Id
    private String id;
    private String article;
    private String writer;
    private String content;
    private boolean liked;

    public DetailPageBean() {
    }

    public DetailPageBean(String id, String article, String writer, String content) {
        this.id = id;
        this.article = article;
        this.writer = writer;
        this.content = content;
    }

    protected DetailPageBean(Parcel in) {
        id = in.readString();
        article = in.readString();
        writer = in.readString();
        content = in.readString();
    }

    @Generated(hash = 838818718)
    public DetailPageBean(String id, String article, String writer, String content,
                          boolean liked) {
        this.id = id;
        this.article = article;
        this.writer = writer;
        this.content = content;
        this.liked = liked;
    }

    public static final Creator<DetailPageBean> CREATOR = new Creator<DetailPageBean>() {
        @Override
        public DetailPageBean createFromParcel(Parcel in) {
            return new DetailPageBean(in);
        }

        @Override
        public DetailPageBean[] newArray(int size) {
            return new DetailPageBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(article);
        dest.writeString(writer);
        dest.writeString(content);
    }

    public boolean getLiked() {
        return this.liked;
    }

    @Override
    public String toString() {
        return "DetailPageBean{" +
                "id='" + id + '\'' +
                ", article='" + article + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", liked=" + liked +
                '}';
    }
}
