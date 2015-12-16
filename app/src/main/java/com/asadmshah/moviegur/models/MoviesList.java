package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import com.asadmshah.moviegur.models.tmdb.MoviesListResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class MoviesList implements Parcelable {

    public static MoviesList create(MoviesListResponse response) {
        List<Movie> movies = new ArrayList<>(response.movies.size());
        for (MoviesListResponse.Movie movie : response.movies) {
            if ("en".equals(movie.language) && movie.backdropPath != null && movie.posterPath != null && movie.overview != null) {
                movies.add(Movie.create(movie));
            }
        }
        return new AutoParcel_MoviesList.Builder()
                .page(response.page)
                .totalPages(response.totalPages)
                .totalResults(response.totalResults)
                .movies(movies)
                .build();
    }

    public abstract int page();
    public abstract int totalPages();
    public abstract int totalResults();
    public abstract List<Movie> movies();

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder page(int i);
        public abstract Builder totalPages(int i);
        public abstract Builder totalResults(int i);
        public abstract Builder movies(List<Movie> lm);
        public abstract MoviesList build();
    }

    @AutoParcel
    public abstract static class Movie implements Parcelable {

        public static Movie create(MoviesListResponse.Movie movie) {
            Builder builder = new AutoParcel_MoviesList_Movie.Builder();
            builder.posterPath(PosterPath.of(movie.posterPath));
            builder.backdropPath(BackdropPath.of(movie.backdropPath));
            builder.id(movie.id);
            builder.title(movie.title);
            builder.overview(movie.overview);
            return builder.build();
        }

        public abstract BackdropPath backdropPath();
        public abstract PosterPath posterPath();
        public abstract long id();
        public abstract String title();
        public abstract String overview();

        @AutoParcel.Builder
        public abstract static class Builder {
            public abstract Builder backdropPath(BackdropPath bp);
            public abstract Builder posterPath(PosterPath pp);
            public abstract Builder id(long l);
            public abstract Builder title(String s);
            public abstract Builder overview(String s);
            public abstract Movie build();
        }
    }

}
