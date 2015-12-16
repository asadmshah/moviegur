package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.analytics.Analytics;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockAnalyticsModule {

    @Provides
    @Singleton
    public Analytics providesAnalytics() {
        return Mockito.mock(Analytics.class);
    }

}
