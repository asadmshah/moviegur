package com.asadmshah.moviegur.injection.modules;

import android.app.Application;

import com.asadmshah.moviegur.preferences.PreferencesStore;
import com.asadmshah.moviegur.preferences.PreferencesStoreImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    public PreferencesStore providesPreferencesStore(Application application) {
        return new PreferencesStoreImpl(application);
    }
}
