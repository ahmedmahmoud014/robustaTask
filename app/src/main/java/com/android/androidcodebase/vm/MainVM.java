package com.android.androidcodebase.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.model.WeatherResponse;
import com.android.androidcodebase.data.repository.MainDataRepository;
import com.android.androidcodebase.di.module.MainVMModule;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.utils.ValidateApiResponse;
import com.android.androidcodebase.utils.enums.FragmentsEnum;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Response;

public class MainVM extends AndroidViewModel {

    public PublishSubject<WeatherResponse> weatherInfo = PublishSubject.create();
    public PublishSubject<String> errorMessage  = PublishSubject.create();
    public PublishSubject<Boolean> loader  = PublishSubject.create();
    public PublishSubject<FragmentsEnum> currentFragment  = PublishSubject.create();
    public String cityName = "";
    public byte[] selectedCityWeather = null;


    @Inject
    MainDataRepository mainDataRepository;

    @Inject
    DisplayMessage displayMessage;
    @Inject
    ValidateApiResponse validateApiResponse;

    public MainVM(@NonNull Application application) {
        super(application);
        ((MyApplication) application).getNetComponent().injectManiVm(new MainVMModule(application)).inject(this);
    }


    public void getWeatherInfo() {
        loader.onNext(true);
        final Disposable[] disposable = new Disposable[1];
        mainDataRepository.getWeatherInfo(cityName).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Response<WeatherResponse>>() {
                    @Override
                    public boolean test(Response<WeatherResponse> weatherReponseResponse) throws Exception {
                        loader.onNext(false);
                        if (weatherReponseResponse.code() == 200) {
                            return true;
                        } else if (weatherReponseResponse.code() == 401) {
                            errorMessage.onNext((getStringValue(R.string.not_authorized)  ));
                            return false;

                        } else {
                            errorMessage.onNext(getStringValue(R.string.internal_server_error) );

                            return false;
                        }
                    }
                }).map(new Function<Response<WeatherResponse>, WeatherResponse>() {
            @Override
            public WeatherResponse apply(Response<WeatherResponse> weatherReponseResponse) throws Exception {
                return weatherReponseResponse.body();
            }
        }).subscribe(new Observer<WeatherResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable[0] =  d;
            }

            @Override
            public void onNext(WeatherResponse weatherReponse) {
              weatherInfo.onNext(weatherReponse);
            }

            @Override
            public void onError(Throwable e) {

                errorMessage.onNext(getStringValue(R.string.weather_exception) + e.getMessage() );
            }

            @Override
            public void onComplete() {
                disposable[0].dispose();
            }
        });


    }


    String  getStringValue  (int  id ) {
        return getApplication().getString(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
