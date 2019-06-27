package com.bb.kanjuzi.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Boby on 2019/6/21.
 */

public class SearchInfo {
    @Pick("div.wridesccon")
    String jianJie;
    @Pick(value = "div.searcharimgleft > a > img", attr = Attrs.SRC)
    String imageUrl;
    @Pick("div.xqsearpagerelatarticle > a")
    List<RelativeArticle> relativeArticles;
    @Pick("div.xqsearpagerelatwriter > a")
    List<RelativeWriter> relativeWriters;
    @Pick("div.views-field-phpcode")
    List<Comment> comments;

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

    public List<RelativeArticle> getRelativeArticles() {
        return relativeArticles;
    }

    public void setRelativeArticles(List<RelativeArticle> relativeArticles) {
        this.relativeArticles = relativeArticles;
    }

    public List<RelativeWriter> getRelativeWriters() {
        return relativeWriters;
    }

    public void setRelativeWriters(List<RelativeWriter> relativeWriters) {
        this.relativeWriters = relativeWriters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
                "jianJie='" + jianJie + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", relativeArticles=" + relativeArticles +
                ", relativeWriters=" + relativeWriters +
                ", comments=" + comments +
                '}';
    }

    public static class RelativeArticle extends BaseArticle {
        //相关作品
        @Pick()
        String name;
        @Pick(attr = Attrs.HREF)
        String href;

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

        @Override
        public String toString() {
            return "RelativeArticle{" +
                    "name='" + name + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }

    public static class RelativeWriter extends BaseArticle {
        //相关作者
        @Pick()
        String name;
        @Pick(attr = Attrs.HREF)
        String href;

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


        @Override
        public String toString() {
            return "RelativeWriter{" +
                    "name='" + name + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }

    public static class Comment implements MultiItemEntity {
        @Pick(value = "a.xlistju", attr = Attrs.HREF)
        String id;
        @Pick(value = "span.views-field-field-oriarticle-value > a")
        String article;
        @Pick(value = "div.xqjulistwafo > a")
        String writer;
        @Pick(value = "a.xlistju")
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
        public String toString() {
            return "Comment{" +
                    "name='" + article + '\'' +
                    ", writer='" + writer + '\'' +
                    ", content='" + content + '\'' +
                    ", hot='" + hot + '\'' +
                    ", fromWho='" + fromWho + '\'' +
                    '}';
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }
}