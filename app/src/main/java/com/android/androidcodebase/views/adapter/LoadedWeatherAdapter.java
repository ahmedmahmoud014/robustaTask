package com.android.androidcodebase.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.model.WeatherInfo;
import com.android.androidcodebase.utils.callback.LoadedCityCallBack;
import com.android.androidcodebase.views.view_holder.WeatherViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LoadedWeatherAdapter extends  RecyclerView.Adapter<WeatherViewHolder> {

    List<WeatherInfo> weatherInfos =  new ArrayList<>();
    Context context;
    LoadedCityCallBack loadedCityCallBack;



    public LoadedWeatherAdapter(List<WeatherInfo> weatherInfos, Activity activity ,   LoadedCityCallBack loadedCityCallBack) {
        this.weatherInfos = weatherInfos;
        this.context = activity;
        this.loadedCityCallBack =  loadedCityCallBack;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.loaded_weather_row, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherInfo  weatherInfo =  weatherInfos.get(position);
        holder.shareWeather.setText(weatherInfo.getCityName());
        if (weatherInfo.getWeatherImage() != null) {
            Log.d("bitmapBitmap","" + weatherInfo.getWeatherImage());
            Bitmap bmp = BitmapFactory.decodeByteArray(weatherInfo.getWeatherImage(), 0, weatherInfo.getWeatherImage().length);
            holder.weatherImage.setImageBitmap(bmp);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadedCityCallBack.loadedCityAction(weatherInfo);
            }
        });

    }

    public Bitmap StringToBitMap(String encodedString){


        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Log.d("bitmapBitmap" , "" + bitmap);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return weatherInfos.size();
    }
}
