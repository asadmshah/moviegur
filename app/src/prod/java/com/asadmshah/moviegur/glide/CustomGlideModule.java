package com.asadmshah.moviegur.glide;

import android.content.Context;

import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.models.PosterPath;
import com.asadmshah.moviegur.models.ProfilePath;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

public class CustomGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(BackdropPath.class, InputStream.class, new BackdropLoader.Factory());
        glide.register(PosterPath.class, InputStream.class, new PosterLoader.Factory());
        glide.register(ProfilePath.class, InputStream.class, new ProfileLoader.Factory());
    }

}
