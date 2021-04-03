package com.android.androidcodebase.di.component;


import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.di.module.MainVMModule;
import com.android.androidcodebase.views.fragment.BaseFragment;
import com.android.androidcodebase.vm.MainVM;

import dagger.Module;
import dagger.Subcomponent;

@Subcomponent (modules = {MainVMModule.class})
public interface MainVmComponent {

    void inject (MainVM mainVM);
    void inject (BaseFragment baseFragment);


}
