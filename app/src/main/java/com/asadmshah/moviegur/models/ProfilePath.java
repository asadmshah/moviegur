package com.asadmshah.moviegur.models;

import android.os.Parcelable;

import auto.parcel.AutoParcel;

@AutoParcel
public abstract class ProfilePath implements Parcelable {

    public static ProfilePath of(String path) {
        return new AutoParcel_ProfilePath(path);
    }

    public abstract String path();
}
