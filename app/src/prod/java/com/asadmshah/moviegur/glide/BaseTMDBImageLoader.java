package com.asadmshah.moviegur.glide;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

abstract class BaseTMDBImageLoader<T> extends BaseGlideUrlLoader<T> {

    private static final String BASE_URL = "https://image.tmdb.org/t/p";

    public BaseTMDBImageLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    protected String getUrl(T model, int width, int height) {
        String path = getPath(model);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return BASE_URL + "/" + getSize(width, height) + "/" + path;
    }

    abstract String getPath(T model);

    abstract String getSize(int width, int height);

}
