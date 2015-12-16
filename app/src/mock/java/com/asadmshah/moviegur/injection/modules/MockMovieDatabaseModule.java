package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.network.MovieDatabaseClient;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockMovieDatabaseModule {

    @Provides
    @Singleton
    public MovieDatabaseClient providesMovieDatabaseClient() {
        return Mockito.mock(MovieDatabaseClient.class);
    }

}
