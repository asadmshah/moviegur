package com.asadmshah.moviegur.screens.about;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asadmshah.moviegur.R;

class LibraryItemViewHolder extends RecyclerView.ViewHolder implements AboutScreenContract.LibraryItemViewHolder {

    private final TextView viewTitle;
    private final TextView viewDescription;

    LibraryItemViewHolder(View itemView) {
        super(itemView);

        viewTitle = (TextView) itemView.findViewById(R.id.title);
        viewDescription = (TextView) itemView.findViewById(R.id.description);
    }

    @Override
    public void setTitle(String title) {
        viewTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        viewDescription.setText(description);
    }
}
