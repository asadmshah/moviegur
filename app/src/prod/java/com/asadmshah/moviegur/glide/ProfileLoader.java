package com.asadmshah.moviegur.glide;

import android.content.Context;

import com.asadmshah.moviegur.models.ProfilePath;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;

/**
 * Created by asadmshah on 1/2/16.
 */
public class ProfileLoader extends BaseTMDBImageLoader<ProfilePath> {

    static final String W45 = "w45";
    static final String W185 = "w154";
    static final String ORIGINAL = "original";

    public ProfileLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    String getPath(ProfilePath model) {
        return model.path();
    }

    @Override
    String getSize(int width, int height) {
        String size;
        if (width == Target.SIZE_ORIGINAL) {
            size = ORIGINAL;
        } else if (width <= 45) {
            size = W45;
        } else if (width <= 185) {
            size = W185;
        } else {
            size = ORIGINAL;
        }
        return size;
    }

    static class Factory implements ModelLoaderFactory<ProfilePath, InputStream> {
        @Override
        public ModelLoader<ProfilePath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new ProfileLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {

        }
    }
}
