package com.asadmshah.moviegur.screens.movies_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.models.BackdropPath;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

class MoviesListScreenMovieItemViewHolder extends RecyclerView.ViewHolder implements MoviesListScreenContract.MovieItemViewHolder {

    private final ImageView viewPoster;
    private final TextView viewTitle;

    private final RequestManager requestManager;

    MoviesListScreenMovieItemViewHolder(View itemView) {
        super(itemView);
        viewPoster = (ImageView) itemView.findViewById(R.id.poster);
        viewTitle = (TextView) itemView.findViewById(R.id.title);

        requestManager = Glide.with(itemView.getContext());
    }

    @Override
    public void setTitle(String title) {
        viewTitle.setText(title);
    }

    @Override
    public void setBackdrop(BackdropPath backdrop) {
        requestManager
                .load(backdrop)
                .thumbnail(requestManager.load(backdrop).thumbnail(0.15f).centerCrop())
                .centerCrop()
                .dontAnimate()
                .into(viewPoster);
    }

}
