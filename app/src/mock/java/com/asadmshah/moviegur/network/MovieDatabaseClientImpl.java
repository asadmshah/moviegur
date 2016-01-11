package com.asadmshah.moviegur.network;

import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.models.tmdb.MovieResponse;
import com.asadmshah.moviegur.models.tmdb.MoviesListResponse;
import com.asadmshah.moviegur.utils.TestResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;

public class MovieDatabaseClientImpl implements MovieDatabaseClient {

    @Override
    public Observable<MoviesList> getNowPlayingMovies(int page) {
        String filename = String.format("tmdb_now_playing_%d.json", normalizePage(page));
        return getMoviesListObservableFromFile(filename);
    }

    @Override
    public Observable<MoviesList> getPopularMovies(int page) {
        String filename = String.format("tmdb_popular_%d.json", normalizePage(page));
        return getMoviesListObservableFromFile(filename);
    }

    @Override
    public Observable<MoviesList> getTopRatedMovies(int page) {
        String filename = String.format("tmdb_top_rated_%d.json", normalizePage(page));
        return getMoviesListObservableFromFile(filename);
    }

    @Override
    public Observable<MoviesList> getUpcomingMovies(int page) {
        String filename = String.format("tmdb_upcoming_%d.json", normalizePage(page));
        return getMoviesListObservableFromFile(filename);
    }

    @Override
    public Observable<Movie> getMovie(long id) {
        return Observable.defer(() -> {
            Observable<MovieResponse> response = getResponseObservableFromFile("tmdb_movie.json", MovieResponse.class);
            return response.map(Movie::create);
        });
    }

    private int normalizePage(int page) {
        return page <= 1 ? 1 : page >= 3 ? 3 : 2;
    }

    private Observable<MoviesList> getMoviesListObservableFromFile(final String filename) {
        return Observable.defer(() -> {
            Observable<MoviesListResponse> response = getResponseObservableFromFile(filename, MoviesListResponse.class);
            return response.map(MoviesList::create);
        });
    }

    private <T> Observable<T> getResponseObservableFromFile(String filename, Class<T> tClass) {
        InputStream inputStream = TestResourceLoader.load(this, filename);
        try {
            T response = LoganSquare.parse(inputStream, tClass);
            return Observable.just(response);
        } catch (IOException e) {
            return Observable.error(e);
        }
    }

}
