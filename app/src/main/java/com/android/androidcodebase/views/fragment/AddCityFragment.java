package com.android.androidcodebase.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.androidcodebase.R;
import com.android.androidcodebase.utils.enums.FragmentsEnum;
import com.android.androidcodebase.views.activiyty.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddCityFragment extends BaseFragment {
    @BindView(R.id.cityName)
    EditText cityName;

    @Override
    int layout() {
        return R.layout.city_layout;
    }

    @Override
    void assignItems() {


    }


    @OnClick(R.id.loadWeatherData)
    public void onViewClicked() {
        ((MainActivity)getActivity()).mainVM.selectedCityWeather = null;
        ((MainActivity) getActivity()).mainVM.cityName = cityName.getText().toString();
        ((MainActivity) getActivity()).mainVM.currentFragment.onNext(FragmentsEnum.WEATHER);
    }
}
