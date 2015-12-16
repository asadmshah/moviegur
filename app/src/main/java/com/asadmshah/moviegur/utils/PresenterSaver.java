package com.asadmshah.moviegur.utils;

import android.os.Parcelable;

import java.util.ArrayList;

public interface PresenterSaver {

    void putParcelableArrayList(String key, ArrayList<? extends Parcelable> list);

    void putParcelable(String key, Parcelable value);

    void putInt(String key, int value);

    void putBoolean(String key, boolean value);
}
