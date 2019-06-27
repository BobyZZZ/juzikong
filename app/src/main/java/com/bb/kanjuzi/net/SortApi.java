package com.bb.kanjuzi.net;

import com.bb.kanjuzi.bean.SearchInfo;
import com.bb.kanjuzi.bean.SortBean;
import com.bb.kanjuzi.bean.SortDetailInfo;
import com.bb.kanjuzi.bean.SquareTheNewest;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 分类
 * Created by Boby on 2019/6/19.
 */

public interface SortApi {
    /**
     * 书籍
     *
     * @param page
     * @return
     */
    @GET("/books")
    @Html
    Observable<SortBean> getBooks(@Query("page") int page);

    /**
     * 电影
     *
     * @param page
     * @return
     */
    @GET("/allarticle/jingdiantaici")
    @Html
    Observable<SortBean> getMovies(@Query("page") int page);

    /**
     * 散文
     *
     * @param page
     * @return
     */
    @GET("/allarticle/sanwen")
    @Html
    Observable<SortBean> getSanWens(@Query("page") int page);

    /**
     * 动漫
     *
     * @param page
     * @return
     */
    @GET("/allarticle/dongmantaici")
    @Html
    Observable<SortBean> getDongMans(@Query("page") int page);

    /**
     * 电视剧
     *
     * @param page
     * @return
     */
    @GET("/lianxujutaici")
    @Html
    Observable<SortBean> getSoaps(@Query("page") int page);

    /**
     * 古诗词
     *
     * @param page
     * @return
     */
    @GET("/allarticle/guwen")
    @Html
    Observable<SortBean> getPoetys(@Query("page") int page);

    /**
     * 分类页中某项详细信息页
     *
     * @param url
     * @param page
     * @return
     */
    @GET
    @Html
    Observable<SortDetailInfo> getSortDetailInfo(@Url String url, @Query("page") int page);


    @GET("/search/node/{key}?type:sentence")
    @Html
    @Headers("Referer:http://www.juzimi.com/search/node/%s/page=-1")//Referer:告诉服务器我是从哪个页面链接过来的,防盗链
    Observable<SearchInfo> getSearchPage(@Path(value = "key", encoded = true) String key, @Query("page") int page);
}
