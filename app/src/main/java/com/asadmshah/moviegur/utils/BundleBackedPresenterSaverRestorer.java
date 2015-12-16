package com.asadmshah.moviegur.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

public class BundleBackedPresenterSaverRestorer implements PresenterSaver, PresenterRestorer {

    private final Bundle bundle;

    public BundleBackedPresenterSaverRestorer(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void putParcelableArrayList(String key, ArrayList<? extends Parcelable> list) {
        bundle.putParcelableArrayList(key, list);
    }

    @Override
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key) {
        return bundle.getParcelableArrayList(key);
    }

    @Override
    public void putParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);
    }

    @Override
    public <T extends Parcelable> T getParcelable(String key) {
        return bundle.getParcelable(key);
    }

    @Override
    public void putInt(String key, int value) {
        bundle.putInt(key, value);
    }

    @Override
    public int getInt(String key) {
        return bundle.getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return bundle.getInt(key, defaultValue);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        bundle.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return bundle.getBoolean(key, defaultValue);
    }

}
