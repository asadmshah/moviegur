package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import com.asadmshah.moviegur.models.tmdb.MediaImagesResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class MediaImages implements Parcelable {

    public static MediaImages create(MediaImagesResponse mediaImagesResponse) {
        List<BackdropPath> backdrops;
        if (mediaImagesResponse.backdrops != null) {
            backdrops = new ArrayList<>(mediaImagesResponse.backdrops.size());
            for (MediaImagesResponse.Image image : mediaImagesResponse.backdrops) {
                backdrops.add(BackdropPath.of(image.path));
            }
        } else {
            backdrops = new ArrayList<>();
        }

        List<PosterPath> posters;
        if (mediaImagesResponse.posters != null) {
            posters = new ArrayList<>(mediaImagesResponse.posters.size());
            for (MediaImagesResponse.Image image : mediaImagesResponse.posters) {
                posters.add(PosterPath.of(image.path));
            }
        } else {
            posters = new ArrayList<>();
        }

        return new AutoParcel_MediaImages(backdrops, posters);
    }

    public abstract List<BackdropPath> backdrops();
    public abstract List<PosterPath> posters();
}
