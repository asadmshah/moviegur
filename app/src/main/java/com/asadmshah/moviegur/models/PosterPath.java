package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class PosterPath implements Parcelable {

    public static PosterPath of(String path) {
        return new AutoParcel_PosterPath(path);
    }

    public abstract String path();

}
