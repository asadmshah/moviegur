package com.asadmshah.moviegur.utils;

public class ResourceSupplierImpl implements ResourceSupplier {

    @Override
    public String[] getLibraryTitles() {
        return new String[]{
                "Library Title 1",
                "Library Title 2",
                "Library Title 3"
        };
    }

    @Override
    public String[] getLibraryDescriptions() {
        return new String[]{
                "Library Description 1",
                "Library Description 2",
                "Library Description 3"
        };
    }
}
