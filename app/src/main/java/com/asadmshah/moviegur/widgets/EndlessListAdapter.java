package com.asadmshah.moviegur.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.asadmshah.moviegur.R;

public abstract class EndlessListAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_LOADER = 2983;

    @Override
    public final int getItemViewType(int position) {
        return position < getContentCount() ? getContentViewType(position) : VIEW_TYPE_LOADER;
    }

    public int getContentViewType(int position) {
        return 0;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_LOADER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.widget_endlesslistadapter_viewholder, parent, false);
            vh = new LoaderViewHolder(view);
        } else {
            vh = onCreateContentViewHolder(parent, viewType);
        }
        return vh;
    }

    public abstract T onCreateContentViewHolder(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LOADER) {
            // Do something.
        } else {
            @SuppressWarnings("unchecked") T h = (T) holder;
            onBindContentViewHolder(h, position);
        }
    }

    public abstract void onBindContentViewHolder(T holder, int position);

    @Override
    public final int getItemCount() {
        return 1 + getContentCount();
    }

    public abstract int getContentCount();

    private static final class LoaderViewHolder extends RecyclerView.ViewHolder {
        final ProgressBar viewProgressBar;
        public LoaderViewHolder(View itemView) {
            super(itemView);
            viewProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

}
