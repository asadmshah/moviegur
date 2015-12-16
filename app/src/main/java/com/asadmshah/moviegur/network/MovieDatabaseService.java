package com.asadmshah.moviegur.network;

import com.asadmshah.moviegur.models.tmdb.MovieResponse;
import com.asadmshah.moviegur.models.tmdb.MoviesListResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface MovieDatabaseService {

    @GET("movie/now_playing")
    Observable<MoviesListResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Observable<MoviesListResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<MoviesListResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Observable<MoviesListResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}?append_to_response=images,reviews,credits,videos")
    Observable<MovieResponse> getMovie(@Path("id") long id, @Query("api_key") String apiKey);

}
