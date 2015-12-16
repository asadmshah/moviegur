package com.asadmshah.moviegur.eventbus;

public class EventBusImpl implements EventBus {

    private final de.greenrobot.event.EventBus eventBus;

    public EventBusImpl(de.greenrobot.event.EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object subscriber) {
        eventBus.post(subscriber);
    }
}
