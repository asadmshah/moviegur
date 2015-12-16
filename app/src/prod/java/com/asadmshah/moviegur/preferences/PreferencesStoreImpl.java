package com.asadmshah.moviegur.preferences;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesStoreImpl implements PreferencesStore {

    private final SharedPreferences preferences;

    public PreferencesStoreImpl(Application application) {
        preferences = PreferenceManager.getDefaultSharedPreferences(application);
    }

}
