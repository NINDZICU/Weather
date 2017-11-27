package com.kfpu.weather;

import android.support.annotation.Nullable;

import com.kfpu.weather.pojo.Weather;

/**
 * Created by hlopu on 27.11.2017.
 */

public interface UpdateWeather {
    public void updateWeather();
    public void onNetworkLoadingFailure();
    public void onNetworkLoadingSuccess(@Nullable Weather result);
}
