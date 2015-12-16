package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import com.asadmshah.moviegur.models.tmdb.GenreResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Genre implements Parcelable {

    public static List<Genre> createList(List<GenreResponse> genreResponses) {
        if (genreResponses == null) {
            return new ArrayList<>();
        }
        List<Genre> genres = new ArrayList<>(genreResponses.size());
        for (GenreResponse genreResponse : genreResponses) {
            genres.add(create(genreResponse));
        }
        return genres;
    }

    public static Genre create(GenreResponse response) {
        return new AutoParcel_Genre(response.id, response.name);
    }

    public abstract int id();
    public abstract String name();
}
