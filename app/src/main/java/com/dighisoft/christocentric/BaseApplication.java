package com.dighisoft.christocentric;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

import Models.BranchDatabase;
import Models.InvestmentDatabase;
import Models.MemberDatabase;
import Models.PaymentDatabase;
import Models.UserDBModel;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);

        DatabaseConfig appDatabase = new DatabaseConfig.Builder(AppDatabase.class)
                .addModelClasses(InvestmentDatabase.class, BranchDatabase.class,
                        UserDBModel.class, MemberDatabase.class, PaymentDatabase.class)
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }


}
