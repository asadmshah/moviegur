package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class CreditsResponse {

    @JsonField
    public List<CastMemberResponse> cast;

    @JsonField
    public List<CrewMemberResponse> crew;

}
