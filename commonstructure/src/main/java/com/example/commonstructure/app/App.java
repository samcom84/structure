package com.example.commonstructure.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.view.Display;
import android.view.WindowManager;

import com.example.commonstructure.MyLibraryModule;
import com.example.commonstructure.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends MultiDexApplication {
    public static final String TAG = App.class.getSimpleName();
    public static int windowHeight;
    private static App _intance = null;
    Activity activity;

    public App() {
        _intance = this;
    }

    public static Context getContext() {
        return _intance;
    }

    public static synchronized App getInstance() {
        return _intance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        MultiDex.install(this);
        windowManager();
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void windowManager() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        windowHeight = display.getHeight();
    }
}