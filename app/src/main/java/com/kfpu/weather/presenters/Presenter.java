package com.kfpu.weather.presenters;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.kfpu.weather.Const;
import com.kfpu.weather.UpdateWeather;
import com.kfpu.weather.loader.WeatherLoader;
import com.kfpu.weather.pojo.Weather;

/**
 * Created by hlopu on 27.11.2017.
 */

public class Presenter implements LoaderManager.LoaderCallbacks<Weather> {
    private Context context;
    private UpdateWeather updateWeather;

    public Presenter(@NonNull UpdateWeather updateWeather) {
        this.updateWeather= updateWeather;
    }

    public void buttonClicked(){
        updateWeather.updateWeather();
    }

    @Override
    public Loader<Weather> onCreateLoader(int id, Bundle args) {
        return new WeatherLoader((Context)updateWeather);
    }

    @Override
    public void onLoadFinished(Loader<Weather> loader, Weather data) {
        if(data!= null) {
            switch (loader.getId()) {
                case Const.WEATHER_LOADER_ID:
                    updateWeather.onNetworkLoadingSuccess(data);
                    break;
            }
        }
        else{
            updateWeather.onNetworkLoadingFailure();
        }
    }


    @Override
    public void onLoaderReset(Loader<Weather> loader) {

    }

}
