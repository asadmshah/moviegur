package com.asadmshah.moviegur.utils;

import android.content.Context;

import com.asadmshah.moviegur.R;

public class ResourceSupplierImpl implements ResourceSupplier {

    private final Context context;

    public ResourceSupplierImpl(Context context) {
        this.context = context;
    }

    @Override
    public String[] getLibraryTitles() {
        return context.getResources().getStringArray(R.array.library_titles);
    }

    @Override
    public String[] getLibraryDescriptions() {
        return context.getResources().getStringArray(R.array.library_descriptions);
    }
}
