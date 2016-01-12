package com.asadmshah.moviegur.screens.about;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;

public interface AboutScreenContract {

    interface View {

        void notifyDataSetChanged();

        void navigateUpFromSameTask();
    }

    interface Presenter extends LibrariesDataSource {

        void onCreate(ApplicationGraph graph, View view);

        boolean onOptionsItemSelected(int itemId);

        void onDestroy();
    }

    interface LibrariesDataSource {

        int VIEW_TYPE_LIBRARY_ITEM = 1;
        int VIEW_TYPE_HEADER_ITEM = 2;

        int getLibrariesCount();

        int getLibraryItemType(int position);

        void onBindViewHolder(LibraryItemViewHolder vh, int position);
    }

    interface LibraryItemViewHolder {

        void setTitle(String title);

        void setDescription(String description);
    }

}
