package com.android.androidcodebase.utils;

import android.content.Context;
import android.widget.Toast;

public class DisplayMessage {
    MyApplication myApplication;

    public DisplayMessage(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

   public  void displayToast (String message ) {
        Toast.makeText(myApplication,message,Toast.LENGTH_LONG).show();

    }
}
