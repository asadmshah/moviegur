package com.asadmshah.moviegur.injection.modules;

import android.app.Application;

import com.asadmshah.moviegur.analytics.Analytics;
import com.asadmshah.moviegur.analytics.AnalyticsImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AnalyticsModule {

    @Provides
    @Singleton
    public Analytics providesAnalytics(Application application) {
        return new AnalyticsImpl(application);
    }

}
