package com.asadmshah.moviegur.injection.modules;

import android.app.Application;

import com.asadmshah.moviegur.MoviegurApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviegurApplicationModule {

    private final MoviegurApplication application;

    public MoviegurApplicationModule(MoviegurApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }

}
