package com.task.booksapp.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * used to build retrofit builder without ssl pinning
 */
public class HttpRetrofit {

    /***
     *
     * @param baseUrl
     * @return Retrofit using the below method
     */
    public Retrofit getRetrofit(String baseUrl) {
        return buildRetrofit(baseUrl);
    }

    /***
     *
     * @param baseUrl
     * @return Retrofit object first it depend on the RetrofitBuilder which is in the parent class BaseRetrofit
     * and we just add baseurl to it and build retrofit object
     *
     */
    private Retrofit buildRetrofit(String baseUrl) {

        return getRetrofitBuilder().baseUrl(baseUrl).client(new OkHttpClient.Builder().build()).build();
    }

    protected Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

}
