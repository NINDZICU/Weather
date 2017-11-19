package com.kfpu.weather;

import android.app.Application;
import android.support.annotation.NonNull;

import com.kfpu.weather.api.RetrofitService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hlopu on 19.11.2017.
 */

public class AppDelegate extends Application {
    private RetrofitService retrofit;
    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);

    }

    @NonNull
    public RetrofitService getRetrofit() {
        return retrofit;
    }
}
