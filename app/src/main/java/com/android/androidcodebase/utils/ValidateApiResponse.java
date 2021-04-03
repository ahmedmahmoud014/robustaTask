package com.android.androidcodebase.utils;

import android.content.Context;

import com.android.androidcodebase.data.model.WeatherResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ValidateApiResponse {

   public  Observable<Response<Object>> filterErrorCode  (Observable<Response<Object>> body , Context context) {
      return   body.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Response<Object>>() {
                    @Override
                    public boolean test(Response<Object> objectResponse) throws Exception {
                        if (objectResponse.code() ==  200){
                            if (objectResponse.body() != null){
                                return true;
                            }else {
                                return false;
                            }
                        }else if (objectResponse.code() ==  401)
                        {
                            return false;

                        }else {
                            return false;
                        }                    }
                });
    }

//    public Observable<Response<User>> filterErrorCode(Observable<Response<User>> response) {
//        return   response.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(( new Predicate<Response<User>>() {
//                    @Override
//                    public boolean test(Response<User> objectResponse) throws Exception {
//                        if (objectResponse.code() ==  200){
//                            if (objectResponse.body() != null){
//                                return true;
//                            }else {
//                                return false;
//                            }
//                        }else if (objectResponse.code() ==  401)
//                        {
//                            return false;
//
//                        }else {
//                            return false;
//                        }                    }
//                }));
//    }

    public Observable<Response<ArrayList<WeatherResponse>>> filterErrorCode(Observable<Response<ArrayList<WeatherResponse>>> allUsers) {
       return allUsers
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .filter(new Predicate<Response<ArrayList<WeatherResponse>>>() {
           @Override
           public boolean test(Response<ArrayList<WeatherResponse>> arrayListResponse) throws Exception {
               if (arrayListResponse.code() ==  200){
                   if (arrayListResponse.body() != null){
                       return true;
                   }else {
                       return false;
                   }
               }else if (arrayListResponse.code() ==  401)
               {
                   return false;

               }else {
                   return false;
               }            }
       });
    }
}


