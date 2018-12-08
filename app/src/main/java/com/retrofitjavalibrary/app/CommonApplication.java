package com.retrofitjavalibrary.app;

import android.content.Context;
import com.example.commonstructure.MyLibraryModule;
import com.example.commonstructure.app.App;
import com.example.commonstructure.servicehelper.ServiceHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CommonApplication extends App {

    private static final String DATABASE_NAME = "common_structure.realm";
    private static final int DATABASE_VERSION = 1;
    private static CommonApplication _intance = null;

    public CommonApplication() {
        _intance = this;
    }

    public static Context getContext() {
        return _intance;
    }

    public static synchronized CommonApplication getInstance() {
        return _intance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration mRealmConfiguration = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(DATABASE_VERSION)
                .modules(new AppModule(),Realm.getDefaultModule())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(mRealmConfiguration);
        Realm.getInstance(mRealmConfiguration);

        ServiceHelper.setBaseUrl("http://109.237.25.22:3066/");

    }
}