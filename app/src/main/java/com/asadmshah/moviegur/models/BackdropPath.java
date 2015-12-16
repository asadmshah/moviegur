package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class BackdropPath implements Parcelable {

    public static BackdropPath of(String path) {
        return new AutoParcel_BackdropPath(path);
    }

    public abstract String path();

}
