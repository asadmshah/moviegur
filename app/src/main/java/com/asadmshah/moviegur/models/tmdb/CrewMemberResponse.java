package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CrewMemberResponse {

    @JsonField
    public String department;

    @JsonField
    public long id;

    @JsonField
    public String job;

    @JsonField
    public String name;

    @JsonField(name = "profile_path")
    public String profilePath;
}
