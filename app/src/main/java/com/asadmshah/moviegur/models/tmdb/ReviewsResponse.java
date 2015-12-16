package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class ReviewsResponse {

    @JsonField(name = "results")
    public List<ReviewResponse> reviews;
}
