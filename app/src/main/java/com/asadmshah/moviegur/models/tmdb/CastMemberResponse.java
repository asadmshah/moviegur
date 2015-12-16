package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CastMemberResponse {

    @JsonField
    public long id;

    @JsonField
    public String character;

    @JsonField
    public String name;

    @JsonField(name = "profile_path")
    public String profilePath;

}
