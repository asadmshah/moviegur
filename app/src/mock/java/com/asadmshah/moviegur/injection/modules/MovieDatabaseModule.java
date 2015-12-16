package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.network.MovieDatabaseClientImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDatabaseModule {

    @Provides
    @Singleton
    public MovieDatabaseClient providesMovieDatabaseClient() {
        return new MovieDatabaseClientImpl();
    }

}
