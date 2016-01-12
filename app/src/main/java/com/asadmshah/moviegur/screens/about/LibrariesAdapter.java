package com.asadmshah.moviegur.screens.about;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asadmshah.moviegur.R;

import static com.asadmshah.moviegur.screens.about.AboutScreenContract.LibrariesDataSource.VIEW_TYPE_HEADER_ITEM;
import static com.asadmshah.moviegur.screens.about.AboutScreenContract.LibrariesDataSource.VIEW_TYPE_LIBRARY_ITEM;

class LibrariesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AboutScreenContract.LibrariesDataSource dataSource;

    public void setDataSource(AboutScreenContract.LibrariesDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSource.getLibraryItemType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case VIEW_TYPE_HEADER_ITEM:
                vh = new LibraryHeaderViewHolder(inflater.inflate(R.layout.viewholder_about_library_header, parent, false));
                break;
            case VIEW_TYPE_LIBRARY_ITEM:
                vh = new LibraryItemViewHolder(inflater.inflate(R.layout.viewholder_about_library_item, parent, false));
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_LIBRARY_ITEM:
                dataSource.onBindViewHolder((LibraryItemViewHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.getLibrariesCount();
    }
}
