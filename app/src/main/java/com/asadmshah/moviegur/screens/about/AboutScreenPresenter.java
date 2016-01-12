package com.asadmshah.moviegur.screens.about;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;

public class AboutScreenPresenter implements AboutScreenContract.Presenter {

    private String[] titles;
    private String[] descriptions;

    private AboutScreenContract.View view;

    @Override
    public void onCreate(ApplicationGraph graph, AboutScreenContract.View view) {
        this.view = view;

        titles = graph.resourceSupplier().getLibraryTitles();
        descriptions = graph.resourceSupplier().getLibraryDescriptions();

        view.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                view.navigateUpFromSameTask();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getLibrariesCount() {
        return Math.min(titles.length, descriptions.length) + 1;
    }

    @Override
    public int getLibraryItemType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER_ITEM : VIEW_TYPE_LIBRARY_ITEM;
    }

    @Override
    public void onBindViewHolder(AboutScreenContract.LibraryItemViewHolder vh, int position) {
        vh.setTitle(titles[position-1]);
        vh.setDescription(descriptions[position-1]);
    }
}
