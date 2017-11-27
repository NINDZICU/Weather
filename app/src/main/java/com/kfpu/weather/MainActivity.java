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
import com.kfpu.weather.presenters.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements UpdateWeather {

    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.tv_degrees)
    TextView tvDegrees;
    private Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);
        unbinder = ButterKnife.bind(this);
        presenter.buttonClicked();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_update)
    public void onUpdateClick() {
        presenter.buttonClicked();
    }

    @Override
    public void onNetworkLoadingSuccess(@Nullable Weather result) {
        tvDegrees.setText(String.valueOf((int) (result.getMain().getTemp() - 273)) + "°C");
        getLoaderManager().destroyLoader(Const.WEATHER_LOADER_ID);
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void updateWeather() {
        btnUpdate.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(Const.WEATHER_LOADER_ID, new Bundle(), presenter);
    }

    @Override
    public void onNetworkLoadingFailure() {
        getLoaderManager().destroyLoader(Const.WEATHER_LOADER_ID);
        btnUpdate.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Network Error", Toast.LENGTH_LONG);
        Log.d("NETWORK ERROR", "NETWORK ERROR");
    }
}
