package com.android.androidcodebase.di.component;


import com.android.androidcodebase.data.repository.BaseDataRepository;
import com.android.androidcodebase.di.module.AppModule;
import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.di.module.MainVMModule;
import com.android.androidcodebase.di.module.NetModule;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.views.activiyty.BaseActivity;
import com.android.androidcodebase.views.fragment.BaseFragment;
import com.android.androidcodebase.vm.MainVM;

import dagger.Component;

/**
 * Created by macbokkpro on 2018-12-24.
 */

@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(DisplayMessage displayMessage);
    void inject(BaseDataRepository dataRepository);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    MainActivityComponent inject  (MainScreenModule mainScreenModule);
    MainVmComponent injectManiVm(MainVMModule mainVMModule);



}
