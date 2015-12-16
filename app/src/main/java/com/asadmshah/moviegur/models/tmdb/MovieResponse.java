package com.asadmshah.moviegur.models.tmdb;

import com.asadmshah.moviegur.utils.TMDBDateTypeConverter;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Date;
import java.util.List;

@JsonObject
public class MovieResponse {

    @JsonField
    public int budget;

    @JsonField
    public List<GenreResponse> genres;

    @JsonField
    public long id;

    @JsonField
    public MediaImagesResponse images;

    @JsonField(name = "imdb_id")
    public String imdbId;

    @JsonField
    public String overview;

    @JsonField(name = "poster_path")
    public String posterPath;

    @JsonField(name = "release_date", typeConverter = TMDBDateTypeConverter.class)
    public Date releaseDate;

    @JsonField
    public long revenue;

    @JsonField
    public int runtime;

    @JsonField
    public String status;

    @JsonField
    public String tagline;

    @JsonField
    public String title;

    @JsonField(name = "popularity")
    float popularity;

    @JsonField(name = "vote_average")
    public float voteAverage;

    @JsonField(name = "vote_count")
    public int voteCount;

    @JsonField
    public ReviewsResponse reviews;

    @JsonField
    public CreditsResponse credits;

}
