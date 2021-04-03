package com.android.androidcodebase.views.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.model.WeatherInfo;
import com.android.androidcodebase.data.model.WeatherResponse;
import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.views.activiyty.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoFragment extends BaseFragment {
    Disposable disposable;
    @BindView(R.id.capturedImage)
    ImageView captureImage ;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo ;
    @Override
    int layout() {
        return R.layout.weather_info_layout;
    }
    byte [] image  = null ;

    @Override
    void assignItems() {
        assignValuesToItems ();
      if (  ((MainActivity)getActivity()).mainVM.selectedCityWeather != null ) {
          image  =  ((MainActivity)getActivity()).mainVM.selectedCityWeather;
          Bitmap bmp = BitmapFactory.decodeByteArray(image,
          0, image.length);
          captureImage.setImageBitmap(bmp);
        }else {
          openCamera();
      }
    }

    void assignValuesToItems () {
        //  dagger inject
        ((MyApplication) getActivity().getApplication()).getNetComponent().inject(new MainScreenModule((MainActivity) getActivity())).inject(this);
        ((MainActivity) this.getActivity()).mainVM.weatherInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(WeatherResponse weatherResponse) {
                        if(isAdded()){
                            captureImage.setImageBitmap(addText(photo ,weatherResponse.getMain().getTemp()+ "°F"
                                    ,weatherResponse.getName(),getString(R.string.feel_like) + weatherResponse.getMain().getFeelsLike() +"°F"
                                    ,weatherResponse.getWeather().get(0).getDescription()
                            ));
                        }



                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }


    private Bitmap addText(Bitmap toEdit , String temp ,  String city  ,String feelLike  ,String description){
        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(dest);
        canvas.drawText(temp  , 10,  70, prepareText(15));
        canvas.drawText(feelLike , 25,  80, prepareText(10));
        canvas.drawText(description , 10,  90, prepareText(12));
        canvas.drawText(city , 25,  110, prepareText(15));
        saveTask(city ,dest);
        return dest;
    }

    byte[]  convertBitmapToImage  (Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }
    private void saveTask(String cityName  ,Bitmap bitmap) {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setCityName(cityName);
                weatherInfo.setWeatherImage(convertBitmapToImage(bitmap));

                //adding to database
                weatherDAO.insert(weatherInfo);

                ((MainActivity)getActivity()).getLoadAllAddItems();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                displayMessage.displayToast("saved");
//
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }


    Paint prepareText  (int textSize) {
        Paint paint = new Paint();  //set the look
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        return  paint;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
             photo = (Bitmap) data.getExtras().get("data");
            ((MainActivity) this.getActivity()).mainVM.getWeatherInfo();


        }


    }


    void openCamera  () {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }


}
