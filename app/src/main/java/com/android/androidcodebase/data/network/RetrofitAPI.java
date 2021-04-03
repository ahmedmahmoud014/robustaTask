package com.android.androidcodebase.data.network;

import com.android.androidcodebase.data.model.WeatherResponse;

import retrofit2.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {


    @GET("weather")
    Observable<Response<WeatherResponse>> getWeatherInfo(@Query("q") String  cityName  , @Query("appid") String  platForm);



}
