package com.asadmshah.moviegur.glide;

import android.content.Context;

import com.asadmshah.moviegur.models.PosterPath;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;

class PosterLoader extends BaseTMDBImageLoader<PosterPath> {

    static final String W92 = "w92";
    static final String W154 = "w154";
    static final String W185 = "w185";
    static final String W342 = "w342";
    static final String W500 = "w500";
    static final String W780 = "w780";
    static final String ORIGINAL = "original";

    PosterLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    String getPath(PosterPath model) {
        return model.path();
    }

    @Override
    String getSize(int width, int height) {
        String size;
        if (width == Target.SIZE_ORIGINAL) {
            size = ORIGINAL;
        } else if (width <= 92) {
            size = W92;
        } else if (width <= 154) {
            size = W154;
        } else if (width <= 185) {
            size = W185;
        } else if (width <= 342) {
            size = W342;
        } else if (width <= 500) {
            size = W500;
        } else if (width <= 780) {
            size = W780;
        } else {
            size = ORIGINAL;
        }
        return size;
    }

    static class Factory implements ModelLoaderFactory<PosterPath, InputStream> {
        @Override
        public ModelLoader<PosterPath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new PosterLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {

        }
    }
}
