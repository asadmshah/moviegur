package com.asadmshah.moviegur.screens.movies_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.utils.OnViewHolderClickListener;
import com.asadmshah.moviegur.widgets.EndlessListAdapter;

class MoviesListScreenAdapter extends EndlessListAdapter<MoviesListScreenMovieItemViewHolder> implements OnViewHolderClickListener {

    private MoviesListScreenContract.MoviesDataSource dataSource;
    private OnViewHolderClickListener onViewHolderClickListener;

    public void setDataSource(MoviesListScreenContract.MoviesDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    public MoviesListScreenMovieItemViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_movies_list_movie_item, parent, false);
        final MoviesListScreenMovieItemViewHolder vh = new MoviesListScreenMovieItemViewHolder(view);
        vh.itemView.setOnClickListener(v -> onViewHolderClickListener.onViewHolderClicked(vh));
        return vh;
    }

    @Override
    public void onBindContentViewHolder(MoviesListScreenMovieItemViewHolder holder, int position) {
        dataSource.onBindViewHolder(holder, position);
    }

    @Override
    public int getContentCount() {
        return dataSource.getMoviesCount();
    }

    @Override
    public void onViewHolderClicked(RecyclerView.ViewHolder viewHolder) {
        if (onViewHolderClickListener != null) {
            onViewHolderClickListener.onViewHolderClicked(viewHolder);
        }
    }

}
