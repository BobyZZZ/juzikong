package com.bb.kanjuzi.net;

import android.util.Log;

import com.bb.kanjuzi.global.Constant;

import java.io.IOException;

import me.ghui.fruit.converter.retrofit.FruitConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Boby on 2019/6/18.
 */

public class NetHelper {
    private static SquareApi mSquareApi;//广场
    private static SortApi mSortApi;//分类
    private static HeaderPictureApi mHeaderApi;//头部图片及文字

    private static void initSquareApi() {
        if (mSquareApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(FruitConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_URL)
                    .client(getClient("SquareApi"))
                    .build();

            mSquareApi = retrofit.create(SquareApi.class);
        }
    }

    public static SquareApi getSquareApi() {
        initSquareApi();
        return mSquareApi;
    }

    private static void initHeaderApi() {
        if (mHeaderApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(FruitConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_PICTURE_URL)
                    .client(getClient("HeaderApi"))
                    .build();

            mHeaderApi = retrofit.create(HeaderPictureApi.class);
        }
    }

    public static HeaderPictureApi getHeaderApi() {
        initHeaderApi();
        return mHeaderApi;
    }

    public static SortApi getSortApi() {
        initSortApi();
        return mSortApi;
    }

    private static void initSortApi() {
        if (mSortApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(FruitConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_URL)
                    .client(getClient("SortApi"))
                    .build();

            mSortApi = retrofit.create(SortApi.class);
        }
    }

    private static OkHttpClient getClient(final String tag) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(tag, "log: " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addNetworkInterceptor(interceptor).build();
    }
}
