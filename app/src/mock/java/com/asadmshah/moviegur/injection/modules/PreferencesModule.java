package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.preferences.PreferencesStore;
import com.asadmshah.moviegur.preferences.PreferencesStoreImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    public PreferencesStore providesPreferencesStore() {
        return new PreferencesStoreImpl();
    }

}
