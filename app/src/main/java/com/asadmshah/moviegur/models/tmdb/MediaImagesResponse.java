package com.asadmshah.moviegur.models.tmdb;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class MediaImagesResponse {

    @JsonField
    public List<Image> backdrops;

    @JsonField
    public List<Image> posters;

    @JsonObject
    public static final class Image {

        @JsonField(name = "file_path")
        public String path;
    }
}
