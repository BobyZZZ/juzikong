package com.bb.kanjuzi.bean;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortBean {
    @Pick("div.views-field-tid")
    List<Image> mImages;

    @Pick("div.views-field-phpcode")
    List<Text> mTexts;

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }

    public List<Text> getTexts() {
        return mTexts;
    }

    public void setTexts(List<Text> texts) {
        mTexts = texts;
    }

    @Override
    public String toString() {
        return "SortBean{" +
                "mImages=" + mImages +
                ", mTexts=" + mTexts +
                '}';
    }

    public static class Image {
        @Pick(value = "div.views-field-tid > a > img", attr = Attrs.SRC)
        String imageSrc;

        public String getImageSrc() {
            return imageSrc;
        }

        public void setImageSrc(String imageSrc) {
            this.imageSrc = imageSrc;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "imageSrc='" + imageSrc + '\'' +
                    '}';
        }
    }

    public static class Text implements Parcelable{
        @Pick(value = "span.xqallarticletilelinkspan > a")
        String artical;
        @Pick(value = "div.views-field-phpcode > a")
        String writer;
        @Pick("div.xqagepawirdesc")
        String content;
        @Pick(value = "div.xqagepawirdesclink > a", attr = Attrs.HREF)
        String href;

        String imageUrl;

        public Text() {

        }

        protected Text(Parcel in) {
            artical = in.readString();
            writer = in.readString();
            content = in.readString();
            href = in.readString();
            imageUrl = in.readString();
        }

        public static final Creator<Text> CREATOR = new Creator<Text>() {
            @Override
            public Text createFromParcel(Parcel in) {
                return new Text(in);
            }

            @Override
            public Text[] newArray(int size) {
                return new Text[size];
            }
        };

        public String getArtical() {
            return artical;
        }

        public void setArtical(String artical) {
            this.artical = artical;
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

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "Text{" +
                    "artical='" + artical + '\'' +
                    ", writer='" + writer + '\'' +
                    ", content='" + content + '\'' +
                    ", href='" + href + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(artical);
            dest.writeString(writer);
            dest.writeString(content);
            dest.writeString(href);
            dest.writeString(imageUrl);
        }
    }
}
