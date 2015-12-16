package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.preferences.PreferencesStore;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockPreferencesModule {

    @Provides
    @Singleton
    public PreferencesStore providesPreferencesStore() {
        return Mockito.mock(PreferencesStore.class);
    }

}
