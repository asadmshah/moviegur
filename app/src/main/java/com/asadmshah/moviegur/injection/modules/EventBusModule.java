package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.BuildConfig;
import com.asadmshah.moviegur.eventbus.EventBus;
import com.asadmshah.moviegur.eventbus.EventBusImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventBusModule {

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return new EventBusImpl(de.greenrobot.event.EventBus.builder()
                .logNoSubscriberMessages(BuildConfig.DEBUG)
                .logSubscriberExceptions(BuildConfig.DEBUG)
                .sendNoSubscriberEvent(BuildConfig.DEBUG)
                .sendSubscriberExceptionEvent(BuildConfig.DEBUG)
                .throwSubscriberException(BuildConfig.DEBUG)
                .build());
    }

}
