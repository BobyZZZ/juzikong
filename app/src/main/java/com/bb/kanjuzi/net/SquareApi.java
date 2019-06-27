package com.bb.kanjuzi.net;

import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SquareTheNewest;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Boby on 2019/6/18.
 */

public interface SquareApi {
    /******************************************广场*********************************************/
    /**
     * 最新上传
     * @param page
     * @return
     */
    @GET("/new")
    @Html
    Observable<SquareTheNewest> getSquareNews(@Query("page") int page);

    /**
     * 今日热门
     * @param page
     * @return
     */
    @GET("/todayhot")
    @Html
    Observable<SquareTheNewest> getSquareHotToday(@Query("page") int page);

    /**
     * 今日推荐
     * @param page
     * @return
     */
    @GET("/recommend")
    @Html
    Observable<SquareTheNewest> getSquareRecommend(@Query("page") int page);

    /**
     * 今日推荐
     * @param page
     * @return
     */
    @GET("/totallike")
    @Html
    Observable<SquareTheNewest> getSquarePopular(@Query("page") int page);

    /***********************************************分类*********************************************/
    /**
     * 书籍
     * @param page
     * @return
     */
    @GET("/books")
    @Html
    Observable<SortBean> getBooks(@Query("page")int page);

    /**
     * 电影
     * @param page
     * @return
     */
    @GET("/allarticle/jingdiantaici")
    @Html
    Observable<SortBean> getMovies(@Query("page")int page);

    /**
     * 散文
     * @param page
     * @return
     */
    @GET("/allarticle/sanwen")
    @Html
    Observable<SortBean> getSanWens(@Query("page")int page);

    /**
     * 动漫
     * @param page
     * @return
     */
    @GET("/allarticle/dongmantaici")
    @Html
    Observable<SortBean> getDongMans(@Query("page")int page);

    /**
     * 电视剧
     * @param page
     * @return
     */
    @GET("/lianxujutaici")
    @Html
    Observable<SortBean> getSoaps(@Query("page")int page);

    /**
     * 古诗词
     * @param page
     * @return
     */
    @GET("/allarticle/guwen")
    @Html
    Observable<SortBean> getPoetys(@Query("page")int page);
}
