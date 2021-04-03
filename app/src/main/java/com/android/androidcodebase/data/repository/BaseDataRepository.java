package com.android.androidcodebase.data.repository;

import com.android.androidcodebase.data.network.RetrofitAPI;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.utils.ValidateApiResponse;

import javax.inject.Inject;

public class BaseDataRepository {
  public MyApplication myApplication;

   @Inject
    RetrofitAPI retrofitAPI;
   @Inject
    ValidateApiResponse validateApiResponse;
   @Inject
    DisplayMessage displayMessage;


    public BaseDataRepository(MyApplication mApplication) {
        this.myApplication = mApplication;
        mApplication.getNetComponent().inject(this);

    }



   // public   void getAllItemName () {
//        Log.d("teeeeeeeeeeest", "dataRepository");
//    }
}
