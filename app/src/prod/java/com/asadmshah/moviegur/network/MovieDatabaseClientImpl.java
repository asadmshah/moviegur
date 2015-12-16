package com.asadmshah.moviegur.network;

import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;

import rx.Observable;

public class MovieDatabaseClientImpl implements MovieDatabaseClient {

    private final MovieDatabaseService service;
    private final String key;

    public MovieDatabaseClientImpl(MovieDatabaseService service, String key) {
        this.service = service;
        this.key = key;
    }

    @Override
    public Observable<MoviesList> getNowPlayingMovies(int page) {
        return Observable.defer(() -> service.getNowPlayingMovies(key, page).map(MoviesList::create));
    }

    @Override
    public Observable<MoviesList> getPopularMovies(int page) {
        return Observable.defer(() -> service.getPopularMovies(key, page).map(MoviesList::create));
    }

    @Override
    public Observable<MoviesList> getTopRatedMovies(int page) {
        return Observable.defer(() -> service.getTopRatedMovies(key, page).map(MoviesList::create));
    }

    @Override
    public Observable<MoviesList> getUpcomingMovies(int page) {
        return Observable.defer(() -> service.getUpcomingMovies(key, page).map(MoviesList::create));
    }

    @Override
    public Observable<Movie> getMovie(long id) {
        return Observable.defer(() -> service.getMovie(id, key).map(Movie::create));
    }
}
