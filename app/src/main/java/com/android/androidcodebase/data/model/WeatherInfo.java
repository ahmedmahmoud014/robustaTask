package com.android.androidcodebase.data.model;

        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.PrimaryKey;

        import java.io.Serializable;

@Entity
public class WeatherInfo  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "city_name")
    private String cityName;

    @ColumnInfo(name = "weather_image")
    private byte[] weatherImage;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public byte[] getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(byte[] weatherImage) {
        this.weatherImage = weatherImage;
    }
}
