package com.bb.kanjuzi.bean;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Boby on 2019/6/18.
 */

public class HeaderPicture {
    @Pick(value = "div.item")
    private List<OneItem> items;

    public List<OneItem> getItems() {
        return items;
    }

    public void setItems(List<OneItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "HeaderPicture{" +
                "items=" + items +
                '}';
    }

    public static class OneItem {
        @Pick(value = "img.fp-one-imagen", attr = Attrs.SRC)
        private String imgSrc;
        @Pick(value = "div.fp-one-cita", attr = Attrs.TEXT)
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        @Override
        public String toString() {
            return "OneItem{" +
                    "imgSrc='" + imgSrc + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }
}
