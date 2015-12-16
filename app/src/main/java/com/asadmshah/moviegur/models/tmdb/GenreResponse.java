package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class GenreResponse {

    @JsonField
    public int id;

    @JsonField
    public String name;
}
