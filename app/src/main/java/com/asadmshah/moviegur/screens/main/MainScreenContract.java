package com.asadmshah.moviegur.screens.main;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.screens.movie_summary.MovieSummaryScreenContract;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenContract;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenListType;
import com.asadmshah.moviegur.utils.PresenterSaveable;

public interface MainScreenContract {

    interface View {

        void setToolbarTitle(int stringResId);

        void showMovieSummaryScreen(MoviesList.Movie movie);

        void blurContainerPrimary(long animationDuration);

        void deBlurContainerPrimary(long animationDuration);

        int getCurrentPage();

        void showAboutScreen();
    }

    interface Presenter extends PresenterSaveable, PagerDataSource, MoviesListScreenContract.EventListener, MovieSummaryScreenContract.EventListener {

        void onCreate(ApplicationGraph graph, View view);

        void onPrepareOptionsMenu();

        boolean onOptionsItemSelected(int itemId);

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();

        void onPageSelected(int page);
    }

    interface PagerDataSource {

        int getPageCount();

        int getPageTitle(int page);

        MoviesListScreenListType getPageType(int page);
    }

}
