package com.android.androidcodebase.views.view_holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.androidcodebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder  extends RecyclerView.ViewHolder{
    @BindView(R.id.weatherImage)
    public ImageView weatherImage;
    @BindView(R.id.city_name)
   public  TextView shareWeather;

    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
