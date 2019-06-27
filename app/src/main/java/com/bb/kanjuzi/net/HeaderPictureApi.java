package com.bb.kanjuzi.net;

import com.bb.kanjuzi.bean.HeaderPicture;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;

/**
 * Created by Boby on 2019/6/18.
 */

public interface HeaderPictureApi {
    @GET("/")
    @Html
    Observable<HeaderPicture> getPictures();
}
