package com.android.androidcodebase.utils.callback;

import com.android.androidcodebase.data.model.Weather;
import com.android.androidcodebase.data.model.WeatherInfo;

public interface LoadedCityCallBack {

    void loadedCityAction (WeatherInfo weatherInfo);
}
