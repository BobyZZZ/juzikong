package com.bb.kanjuzi.utils;

/**
 * Created by Boby on 2019/6/19.
 */

public class TEXTUtils {

    public static String huanHang(String res) {
        String text = res.trim();
        String regex = ".*[a-zA-Z]+.*";
        if (!text.matches(regex)) {
            text = text.replace(" ", "\n");
        }
        return text;
    }
}
