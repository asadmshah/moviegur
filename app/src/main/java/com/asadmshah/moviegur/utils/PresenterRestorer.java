package com.asadmshah.moviegur.utils;

import android.os.Parcelable;

import java.util.ArrayList;

public interface PresenterRestorer {

    <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key);

    <T extends Parcelable> T getParcelable(String key);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);
}
