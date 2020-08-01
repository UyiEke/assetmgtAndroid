package com.coronationmb.retrofitModule;

import com.coronationmb.service.Constant;
import com.coronationmb.service.Utility;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static String BaseUrl= Constant.BaseUrl;

    public static Retrofit retrofit = null;
    public static RetrofitProxyService retrofitProxyService;

    public static Retrofit getClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(4, TimeUnit.MINUTES)
                .writeTimeout(4, TimeUnit.MINUTES)
                .readTimeout(4, TimeUnit.MINUTES)
                .build();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(okHttpClient)
                   // .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public synchronized static RetrofitProxyService getRetrofitInstance(){
        if(retrofit==null){
            retrofitProxyService=RetrofitClient.getClient().create(RetrofitProxyService.class);
        }
        return retrofitProxyService;
    }




}
