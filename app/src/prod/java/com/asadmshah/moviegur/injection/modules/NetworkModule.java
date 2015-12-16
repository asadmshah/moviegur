package com.asadmshah.moviegur.injection.modules;

import android.app.Application;

import com.asadmshah.moviegur.BuildConfig;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    private static final long CACHE_SIZE = 10 * 1024 * 1024;

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(Application application) {
        OkHttpClient client = new OkHttpClient();
        client.setCache(new Cache(application.getCacheDir(), CACHE_SIZE));

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
        client.interceptors().add(logger);

        return client;
    }

}
