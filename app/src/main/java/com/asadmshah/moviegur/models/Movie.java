package com.asadmshah.moviegur.models;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.asadmshah.moviegur.models.tmdb.MovieResponse;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Movie implements Parcelable {

    public static Movie create(MovieResponse response) {
        Builder builder = new AutoParcel_Movie.Builder();
        builder.budget(response.budget);
        builder.credits(Credits.create(response.credits));
        builder.genres(Genre.createList(response.genres));
        builder.id(response.id);
        builder.images(MediaImages.create(response.images));
        builder.overview(response.overview);
        builder.posterPath(PosterPath.of(response.posterPath));
        builder.releaseDate(response.releaseDate.getTime());
        builder.reviews(Review.createList(response.reviews.reviews));
        builder.runtime(response.runtime);
        builder.status(response.status);
        builder.tagLine(response.tagline);
        builder.title(response.title);
        builder.voteAverage(response.voteAverage);
        builder.voteCount(response.voteCount);
        return builder.build();
    }

    public abstract int budget();
    public abstract Credits credits();
    public abstract List<Genre> genres();
    public abstract long id();
    public abstract MediaImages images();
    @Nullable public abstract String overview();
    public abstract PosterPath posterPath();
    public abstract long releaseDate();
    public abstract List<Review> reviews();
    public abstract int runtime();
    @Nullable public abstract String status();
    @Nullable public abstract String tagLine();
    public abstract String title();
    public abstract float voteAverage();
    public abstract int voteCount();

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder budget(int i);
        public abstract Builder credits(Credits credits);
        public abstract Builder genres(List<Genre> l);
        public abstract Builder id(long i);
        public abstract Builder images(MediaImages mi);
        public abstract Builder overview(String s);
        public abstract Builder posterPath(PosterPath p);
        public abstract Builder releaseDate(long l);
        public abstract Builder reviews(List<Review> reviews);
        public abstract Builder runtime(int i);
        public abstract Builder status(String s);
        public abstract Builder tagLine(String s);
        public abstract Builder title(String s);
        public abstract Builder voteAverage(float f);
        public abstract Builder voteCount(int i);
        public abstract Movie build();
    }

}
