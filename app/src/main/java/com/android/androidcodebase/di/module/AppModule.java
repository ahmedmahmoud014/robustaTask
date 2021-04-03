package com.android.androidcodebase.di.module;

import com.android.androidcodebase.data.localDB.DatabaseClient;
import com.android.androidcodebase.data.localDB.WeatherDAO;
import com.android.androidcodebase.data.repository.BaseDataRepository;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.utils.MyApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    MyApplication mApplication;
    public AppModule(MyApplication application) {
        mApplication = application;
    }

    @Provides
    MyApplication providesApplication() {
        return new MyApplication();
    }

    @Provides
    public BaseDataRepository getDataRepository(){
        return  new BaseDataRepository(mApplication);
    }

    @Provides
    public  DisplayMessage  prepareDisplayMessage() {
        return new DisplayMessage(mApplication);
    }

    @Provides
    public WeatherDAO prePareDataBaseClient () {
        return  DatabaseClient.getInstance(mApplication).getAppDatabase()
                .weatherDAO();
    }

}