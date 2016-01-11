package com.asadmshah.moviegur.utils;

import java.io.InputStream;

public class TestResourceLoader {

    private TestResourceLoader() {}

    public static InputStream load(Object obj, String filename) {
        return obj.getClass().getClassLoader().getResourceAsStream(filename);
    }
}
