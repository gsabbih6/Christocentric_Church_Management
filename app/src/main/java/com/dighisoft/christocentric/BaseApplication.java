package com.dighisoft.christocentric;

import android.app.Application;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

import Models.UserDBModel;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseConfig appDatabase = new DatabaseConfig.Builder(AppDatabase.class)
                .addModelClasses(UserDBModel.class)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }


}
