package com.kfpu.weather.api;

import com.kfpu.weather.pojo.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hlopu on 19.11.2017.
 */

public interface RetrofitService {

    @GET("data/2.5/weather")
    Call<Weather> getWeather(@Query("q") String city, @Query("appid") String id);
}
