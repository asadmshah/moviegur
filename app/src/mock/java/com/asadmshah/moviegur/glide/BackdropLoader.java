package com.asadmshah.moviegur.glide;

import android.content.Context;
import android.net.Uri;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.BackdropPath;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.stream.StreamResourceLoader;

import java.io.InputStream;

class BackdropLoader implements ModelLoader<BackdropPath, InputStream> {

    private final ResourceLoader<InputStream> resourceLoader;

    BackdropLoader(Context context, GenericLoaderFactory factory) {
        resourceLoader = new StreamResourceLoader(context, factory.buildModelLoader(Uri.class, InputStream.class));
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(BackdropPath model, int width, int height) {
        return resourceLoader.getResourceFetcher(R.raw.blue, width, height);
    }

    static class Factory implements ModelLoaderFactory<BackdropPath, InputStream> {
        @Override
        public ModelLoader<BackdropPath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new BackdropLoader(context, factories);
        }

        @Override
        public void teardown() {

        }
    }

}
