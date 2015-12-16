package com.asadmshah.moviegur.utils;

import com.bluelinelabs.logansquare.typeconverters.DateTypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TMDBDateTypeConverter extends DateTypeConverter {

    private final DateFormat format;

    public TMDBDateTypeConverter() {
        format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public DateFormat getDateFormat() {
        return format;
    }

}
