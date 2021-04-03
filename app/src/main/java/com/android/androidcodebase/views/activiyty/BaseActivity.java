package com.android.androidcodebase.views.activiyty;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.android.androidcodebase.R;
import com.android.androidcodebase.data.localDB.WeatherDAO;
import com.android.androidcodebase.di.module.MainScreenModule;
import com.android.androidcodebase.utils.DisplayMessage;
import com.android.androidcodebase.utils.MyApplication;
import com.android.androidcodebase.views.fragment.AllLoadedWeatherFragment;
import com.android.androidcodebase.views.fragment.WeatherInfoFragment;
import com.facebook.FacebookSdk;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static com.facebook.FacebookSdk.getApplicationContext;

public abstract class BaseActivity extends AppCompatActivity {

     Disposable loaderDispose  =  null  ;
    Disposable messageDispose  =  null  ;

    ProgressDialog progress=  null;

    @Inject
    DisplayMessage displayMessage;

    @Inject
    WeatherDAO weatherDAO;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout());
        //  inject dagger
        ((MyApplication) getApplication()).getNetComponent().inject(this);
        //  bind butter knife
        ButterKnife.bind(this);
        //  prepare screen items
        assignItems();
        progress  = new ProgressDialog(this);
        progress.setMessage(getString(R.string.loading));

        //
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }



    protected void loadFragment(Fragment fragment  , int replaceLayout) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(replaceLayout, fragment );
        fragmentTransaction .addToBackStack(fragment.getTag());
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            //super.onBackPressed();
            finish();
        }
    }

    // prepare loader
    protected void showLoader  (PublishSubject<Boolean> loader ) {

        loader.subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                loaderDispose =  d;
            }

            @Override
            public void onNext(Boolean aBoolean) {
           if (aBoolean) progress.show();else  progress.dismiss();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
               messageDispose.dispose();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loaderDispose != null) {
            loaderDispose.dispose();
        }

        if (messageDispose !=  null ){
            messageDispose.dispose();
        }
    }

    protected void showMessageDialog (PublishSubject<String> message) {
        message.filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                if (!s.isEmpty()) {
                    return  true ;
                }else  {
                    return  false;
                }
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                loaderDispose =  d;
            }

            @Override
            public void onNext(String s) {
                displayMessage.displayToast(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            loaderDispose.dispose();
            }
        });

    }

    @LayoutRes
   abstract int layout ();
   abstract void  assignItems () ;
}
