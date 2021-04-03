package com.android.androidcodebase.di.component;

import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.utils.GeneralScop;
import com.android.androidcodebase.views.activiyty.MainActivity;
import com.android.androidcodebase.views.fragment.BaseFragment;
import com.android.androidcodebase.views.fragment.WeatherInfoFragment;

import dagger.Subcomponent;

@GeneralScop
@Subcomponent (modules = {MainScreenModule.class})
public interface MainActivityComponent {

    void inject( MainActivity MainActivity);
    void inject (WeatherInfoFragment weatherInfoFragment);

}
