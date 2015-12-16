package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.eventbus.EventBus;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockEventBusModule {

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return Mockito.mock(EventBus.class);
    }

}
