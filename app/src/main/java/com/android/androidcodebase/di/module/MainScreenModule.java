package com.android.androidcodebase.di.module;


import android.content.Context;

import androidx.lifecycle.ViewModelProviders;

import com.android.androidcodebase.data.localDB.DatabaseClient;
import com.android.androidcodebase.data.localDB.WeatherDAO;
import com.android.androidcodebase.data.repository.MainDataRepository;
import com.android.androidcodebase.utils.GeneralScop;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.views.activiyty.MainActivity;
import com.android.androidcodebase.vm.MainVM;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {

    MainActivity mainActivity;


    public MainScreenModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }






    @Provides
    MainVM prepareVm () {
        return ViewModelProviders.of(mainActivity).get(MainVM.class);
    }


//

}
