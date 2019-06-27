package com.bb.kanjuzi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Boby on 2019/6/19.
 */

public class SortBeanItem implements Parcelable,MultiItemEntity{
    String imageUrl;
    SortBean.Text mText;
    int itemType;

    public SortBeanItem(String imageUrl, SortBean.Text text) {
        this.imageUrl = imageUrl;
        mText = text;
    }

    protected SortBeanItem(Parcel in) {
        imageUrl = in.readString();
        mText = in.readParcelable(SortBean.Text.class.getClassLoader());
    }

    public static final Creator<SortBeanItem> CREATOR = new Creator<SortBeanItem>() {
        @Override
        public SortBeanItem createFromParcel(Parcel in) {
            return new SortBeanItem(in);
        }

        @Override
        public SortBeanItem[] newArray(int size) {
            return new SortBeanItem[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SortBean.Text getText() {
        return mText;
    }

    public void setText(SortBean.Text text) {
        mText = text;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeParcelable(mText,flags);
    }

    @Override
    public String toString() {
        return "SortBeanItem{" +
                "imageUrl='" + imageUrl + '\'' +
                ", mText=" + mText +
                '}';
    }
}
