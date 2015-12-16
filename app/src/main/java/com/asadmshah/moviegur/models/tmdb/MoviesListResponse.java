package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MoviesListResponse {

    @JsonField(name = "page")
    public int page;

    @JsonField(name = "results")
    public List<Movie> movies;

    @JsonField(name = "total_pages")
    public int totalPages;

    @JsonField(name = "total_results")
    public int totalResults;

    @JsonObject
    public static final class Movie {

        @JsonField(name = "original_language")
        public String language;

        @JsonField(name = "backdrop_path")
        public String backdropPath;

        @JsonField(name = "poster_path")
        public String posterPath;

        @JsonField(name = "id")
        public long id;

        @JsonField(name = "title")
        public String title;

        @JsonField(name = "overview")
        public String overview;
    }

}
