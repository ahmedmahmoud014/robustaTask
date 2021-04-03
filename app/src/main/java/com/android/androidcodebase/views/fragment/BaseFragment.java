package com.android.androidcodebase.views.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.localDB.WeatherDAO;
import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.views.activiyty.MainActivity;
import com.facebook.FacebookSdk;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;

public abstract  class BaseFragment extends Fragment {
    Unbinder unbinder;
    @Inject
    DisplayMessage displayMessage;
    @Inject
    WeatherDAO weatherDAO;

 private  View screenView =  null ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        screenView =  inflater.inflate(layout(), container, false);
        unbinder =  ButterKnife.bind(this,screenView);

        ((MyApplication) getActivity().getApplication()).getNetComponent().inject(this);


        //  add assign screen items
        assignItems () ;
        return screenView;
    }
    @LayoutRes
    abstract int layout ();
    abstract void assignItems () ;

    public View getScreenView() {
        return screenView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
