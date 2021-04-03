package com.android.androidcodebase.utils;

import android.app.Application;

import com.android.androidcodebase.di.component.DaggerNetComponent;
import com.android.androidcodebase.di.component.NetComponent;
import com.android.androidcodebase.di.module.AppModule;
import com.android.androidcodebase.di.module.NetModule;

public class MyApplication extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(Constants.Config.BASE_URL))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
