package com.android.androidcodebase.views.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.model.WeatherInfo;
import com.android.androidcodebase.utils.callback.LoadedCityCallBack;
import com.android.androidcodebase.utils.enums.FragmentsEnum;
import com.android.androidcodebase.views.activiyty.MainActivity;
import com.android.androidcodebase.views.adapter.LoadedWeatherAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllLoadedWeatherFragment extends BaseFragment  implements LoadedCityCallBack {


    @BindView(R.id.allLoadedWeather)
    RecyclerView allLoadedWeather;

    private LoadedWeatherAdapter loadedWeatherAdapter ;


    @Override
    int layout() {
        return R.layout.all_loaded_weather_layout;
    }

    @Override
    void assignItems() {
        allLoadedWeather.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoadAllAddItems();


    }


    public void getLoadAllAddItems() {
        class GetTasks extends AsyncTask<Void, Void, List<WeatherInfo>> {
            @Override
            protected List<WeatherInfo> doInBackground(Void... voids) {
                List<WeatherInfo> taskList = weatherDAO.getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<WeatherInfo> weatherInfos) {
                super.onPostExecute(weatherInfos);
                prepareRecycler(weatherInfos);

            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }

    void prepareRecycler  (List<WeatherInfo> weatherInfos) {
        loadedWeatherAdapter =  new LoadedWeatherAdapter(weatherInfos,getActivity(),this);
        loadedWeatherAdapter.notifyDataSetChanged();
        allLoadedWeather.setAdapter(loadedWeatherAdapter);
    }

    @Override
    public void loadedCityAction(WeatherInfo weatherInfo) {

        ((MainActivity)getActivity()).mainVM.selectedCityWeather = weatherInfo.getWeatherImage();
        ((MainActivity)getActivity()).mainVM.currentFragment.onNext(FragmentsEnum.WEATHER);
    }




}
