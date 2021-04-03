package com.android.androidcodebase.views.fragment;

import android.view.View;
import android.widget.Button;

import com.android.androidcodebase.R;
import com.android.androidcodebase.utils.enums.FragmentsEnum;
import com.android.androidcodebase.views.activiyty.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InitialScreenFragment extends BaseFragment {


    @BindView(R.id.add_city)
    Button addCity;
    @BindView(R.id.all_loaded_cities)
    Button searchedCity;
    Unbinder unbinder;

    @Override
    int layout() {
        return R.layout.inatial_fragment_step_layout;
    }

    @Override
    void assignItems() {


    }

    @OnClick({R.id.add_city, R.id.all_loaded_cities})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_city:
                ((MainActivity)getActivity()).mainVM.currentFragment.onNext(FragmentsEnum.City);
                break;
            case R.id.all_loaded_cities:
                ((MainActivity)getActivity()).mainVM.currentFragment.onNext(FragmentsEnum.ALL_LOADED_WEATHER);
                break;
        }
    }
}
