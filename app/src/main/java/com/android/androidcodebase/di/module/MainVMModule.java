package com.android.androidcodebase.di.module;


import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.androidcodebase.data.repository.MainDataRepository;
import com.android.androidcodebase.vm.MainVM;

import dagger.Module;
import dagger.Provides;

@Module
public class MainVMModule {

Context context;
    public MainVMModule(Context context) {
        this.context = context;
    }

    @Provides
    MainDataRepository prepareRepository () {
        return new  MainDataRepository (context);
    }

    @Provides
    MainVM prepareVM () {
        return   ViewModelProviders.of((FragmentActivity) context).get(MainVM.class);
    }
}
