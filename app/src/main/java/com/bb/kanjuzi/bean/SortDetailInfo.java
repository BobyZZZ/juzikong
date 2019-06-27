package com.bb.kanjuzi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortDetailInfo {
    String name;
    @Pick("div.wridesccon")
    String jianJie;
    @Pick(value = "div.views-field-tid > img", attr = Attrs.SRC)
    String imageUrl;
    @Pick("span.xqwridesczuopinlinkspan")
    List<Other> others;
    @Pick("div.views-field-phpcode")
    List<Comment> comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJianJie() {
        return jianJie;
    }

    public void setJianJie(String jianJie) {
        this.jianJie = jianJie;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Other> getOthers() {
        return others;
    }

    public void setOthers(List<Other> others) {
        this.others = others;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SortDetailInfo{" +
                "name='" + name + '\'' +
                ", jianJie='" + jianJie + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", others=" + others +
                ", comments=" + comments +
                '}';
    }

    public static class Other extends BaseArticle implements Parcelable {
        @Pick("span.xqwridesczuopinlinkspan > a")
        String name;
        @Pick(value = "span.xqwridesczuopinlinkspan > a", attr = Attrs.HREF)
        String href;

        public Other() {
        }

        public Other(String name, String href) {
            this.name = name;
            this.href = href;
        }

        protected Other(Parcel in) {
            name = in.readString();
            href = in.readString();
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

        public static final Creator<Other> CREATOR = new Creator<Other>() {
            @Override
            public Other createFromParcel(Parcel in) {
                return new Other(in);
            }

            @Override
            public Other[] newArray(int size) {
                return new Other[size];
            }
        };

        @Override
        public String toString() {
            return "Other{" +
                    "name='" + name + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(href);
        }
    }

    public static class Comment implements MultiItemEntity {
        @Pick(value = "a.xlistju", attr = Attrs.HREF)
        String id;
        @Pick(value = "span.views-field-field-oriarticle-value > a")
        String article;
        @Pick(value = "div.xqjulistwafo > a")
        String writer;
        @Pick(value = "div.views-field-phpcode-1 > a")
        String content;
        @Pick(value = "div.views-field-ops > a")
        String hot;
        @Pick(value = "div.views-field-xqname > a")
        String fromWho;
        int itemType;

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

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getFromWho() {
            return fromWho;
        }

        public void setFromWho(String fromWho) {
            this.fromWho = fromWho;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "name='" + article + '\'' +
                    ", writer='" + writer + '\'' +
                    ", content='" + content + '\'' +
                    ", hot='" + hot + '\'' +
                    ", fromWho='" + fromWho + '\'' +
                    '}';
        }
    }
}
