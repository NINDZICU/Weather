package com.kfpu.weather;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kfpu.weather.loader.WeatherLoader;
import com.kfpu.weather.pojo.City;
import com.kfpu.weather.pojo.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Weather>{

    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.tv_degrees)
    TextView tvDegrees;
    private Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        getLoaderManager().initLoader(Const.WEATHER_LOADER_ID, new Bundle(), MainActivity.this);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }
    @OnClick(R.id.btn_update)
    public void onUpdateClick(){
        getLoaderManager().initLoader(Const.WEATHER_LOADER_ID, new Bundle(), MainActivity.this);
    }

    @Override
    public Loader<Weather> onCreateLoader(int id, Bundle args) {
        btnUpdate.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        return new WeatherLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Weather> loader, Weather data) {
        if(data!= null) {
            switch (loader.getId()) {
                case Const.WEATHER_LOADER_ID:
                    onNetworkLoadingSuccess(data);
                    break;
            }
        }
        else{
            onNetworkLoadingFailure();
        }
    }


    @Override
    public void onLoaderReset(Loader<Weather> loader) {

    }
    private void onNetworkLoadingSuccess(@Nullable Weather result) {
        tvDegrees.setText(String.valueOf((int) (result.getMain().getTemp()-273))+"°C");
        getLoaderManager().destroyLoader(Const.WEATHER_LOADER_ID);
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
    private void onNetworkLoadingFailure(){
        getLoaderManager().destroyLoader(Const.WEATHER_LOADER_ID);
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Network Error", Toast.LENGTH_LONG);
        Log.d("NETWORK ERROR", "NETWORK ERROR");
    }
}
