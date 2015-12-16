package com.asadmshah.moviegur.glide;

import android.content.Context;
import android.net.Uri;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.ProfilePath;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.stream.StreamResourceLoader;

import java.io.InputStream;

class ProfileLoader implements ModelLoader<ProfilePath, InputStream> {

    private final ResourceLoader<InputStream> resourceLoader;

    ProfileLoader(Context context, GenericLoaderFactory factory) {
        resourceLoader = new StreamResourceLoader(context, factory.buildModelLoader(Uri.class, InputStream.class));
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(ProfilePath model, int width, int height) {
        return resourceLoader.getResourceFetcher(R.raw.light_green_spot, width, height);
    }

    static class Factory implements ModelLoaderFactory<ProfilePath, InputStream> {
        @Override
        public ModelLoader<ProfilePath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new ProfileLoader(context, factories);
        }

        @Override
        public void teardown() {

        }
    }

}
