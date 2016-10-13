package com.JKSoft.TdbClient.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

/**
 * Created by jirikrejci on 05.10.16.
 */

public class MyApplication extends Application {
    Realm db;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        db = Realm.getDefaultInstance();
    }


}
