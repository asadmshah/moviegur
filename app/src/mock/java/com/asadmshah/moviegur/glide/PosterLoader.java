package com.asadmshah.moviegur.glide;

import android.content.Context;
import android.net.Uri;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.PosterPath;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.stream.StreamResourceLoader;

import java.io.InputStream;

class PosterLoader implements ModelLoader<PosterPath, InputStream> {

    private final ResourceLoader<InputStream> resourceLoader;

    PosterLoader(Context context, GenericLoaderFactory factory) {
        resourceLoader = new StreamResourceLoader(context, factory.buildModelLoader(Uri.class, InputStream.class));
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(PosterPath model, int width, int height) {
        return resourceLoader.getResourceFetcher(R.raw.green_spot, width, height);
    }

    static class Factory implements ModelLoaderFactory<PosterPath, InputStream> {
        @Override
        public ModelLoader<PosterPath, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new PosterLoader(context, factories);
        }

        @Override
        public void teardown() {

        }
    }

}
