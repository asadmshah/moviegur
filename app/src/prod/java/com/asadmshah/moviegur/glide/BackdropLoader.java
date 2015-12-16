package com.asadmshah.moviegur.glide;

import android.content.Context;

import com.asadmshah.moviegur.models.BackdropPath;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;

class BackdropLoader extends BaseTMDBImageLoader<BackdropPath> {

    static final String W300 = "w300";
    static final String W780 = "w780";
    static final String W1280 = "w1280";
    static final String ORIGINAL = "original";

    BackdropLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    String getPath(BackdropPath model) {
        return model.path();
    }

    @Override
    String getSize(int width, int height) {
        String size;
        if (width == Target.SIZE_ORIGINAL) {
            size = ORIGINAL;
        } else if (width <= 300) {
            size = W300;
        } else if (width <= 780) {
            size = W780;
        } else if (width <= 1280) {
            size = W1280;
        } else {
            size = ORIGINAL;
        }
        return size;
    }

    static class Factory implements ModelLoaderFactory<BackdropPath, InputStream> {
        @Override
        public ModelLoader<BackdropPath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new BackdropLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {

        }
    };

}
