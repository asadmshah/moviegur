package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.BuildConfig;
import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.network.MovieDatabaseClientImpl;
import com.asadmshah.moviegur.network.MovieDatabaseService;
import com.github.aurae.retrofit.LoganSquareConverterFactory;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class MovieDatabaseModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String KEY = BuildConfig.TMDB_KEY;

    @Provides
    @Singleton
    public MovieDatabaseService providesMovieDatabaseService(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        return retrofit.create(MovieDatabaseService.class);
    }

    @Provides
    @Singleton
    public MovieDatabaseClient providesMovieDatabaseClient(MovieDatabaseService service) {
        return new MovieDatabaseClientImpl(service, KEY);
    }
}
