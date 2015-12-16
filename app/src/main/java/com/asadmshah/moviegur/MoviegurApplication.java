package com.asadmshah.moviegur;

import android.app.Application;
import android.content.Context;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.injection.components.ApplicationGraphFactory;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MoviegurApplication extends Application {

    private ApplicationGraph component;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = ApplicationGraphFactory.create(this);
    }

    public static ApplicationGraph getGraph(Context context) {
        return ((MoviegurApplication) context.getApplicationContext()).component;
    }

}
