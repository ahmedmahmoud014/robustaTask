package com.android.androidcodebase.data.repository;

import android.content.Context;

import com.android.androidcodebase.data.model.WeatherResponse;
import com.android.androidcodebase.utils.Constants;
import com.android.androidcodebase.utils.MyApplication;

import io.reactivex.Observable;
import retrofit2.Response;

public class MainDataRepository extends BaseDataRepository {


    public MainDataRepository(Context context) {
        super((MyApplication) context);
    }

    public Observable<Response<WeatherResponse>> getWeatherInfo(String cityName) {
        return retrofitAPI.getWeatherInfo(cityName, Constants.Config.APP_ID_WEATHER);
    }


}
