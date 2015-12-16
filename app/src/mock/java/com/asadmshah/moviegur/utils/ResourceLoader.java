package com.asadmshah.moviegur.utils;

import java.io.InputStream;

public class ResourceLoader {

    private ResourceLoader() {}

    public static InputStream load(Object obj, String filename) {
        return obj.getClass().getClassLoader().getResourceAsStream(filename);
    }
}
