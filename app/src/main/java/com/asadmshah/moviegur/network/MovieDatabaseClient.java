package com.asadmshah.moviegur.network;

import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;

import rx.Observable;

public interface MovieDatabaseClient {

    Observable<MoviesList> getNowPlayingMovies(int page);

    Observable<MoviesList> getPopularMovies(int page);

    Observable<MoviesList> getTopRatedMovies(int page);

    Observable<MoviesList> getUpcomingMovies(int page);

    Observable<Movie> getMovie(long id);
}
