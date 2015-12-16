package com.asadmshah.moviegur.screens.movies_list;

import com.asadmshah.moviegur.models.MoviesList;

public final class MoviesListScreenEvents {

    public static final class OnMovieSelect {
        public final MoviesList.Movie movie;

        public OnMovieSelect(MoviesList.Movie movie) {
            this.movie = movie;
        }
    }

}
