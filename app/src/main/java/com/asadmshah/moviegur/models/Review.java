package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import com.asadmshah.moviegur.models.tmdb.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Review implements Parcelable {

    public static List<Review> createList(List<ReviewResponse> responses) {
        if (responses == null) {
            return new ArrayList<>();
        }
        List<Review> reviews = new ArrayList<>(responses.size());
        for (ReviewResponse r : responses) {
            reviews.add(create(r));
        }
        return reviews;
    }

    public static Review create(ReviewResponse response) {
        return new AutoParcel_Review(response.author, response.content);
    }

    public abstract String author();
    public abstract String content();

}
