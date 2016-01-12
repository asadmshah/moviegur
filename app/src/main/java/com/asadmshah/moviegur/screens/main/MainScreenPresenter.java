package com.asadmshah.moviegur.screens.main;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.analytics.Analytics;
import com.asadmshah.moviegur.eventbus.EventBus;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.preferences.PreferencesStore;
import com.asadmshah.moviegur.screens.movie_summary.MovieSummaryScreenEvents;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenEvents;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenListType;
import com.asadmshah.moviegur.utils.PresenterSaver;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class MainScreenPresenter implements MainScreenContract.Presenter {

    private static final MoviesListScreenListType[] PAGE_TYPES_MOVIES = {
            MoviesListScreenListType.NOW_PLAYING,
            MoviesListScreenListType.POPULAR,
            MoviesListScreenListType.TOP_RATED,
            MoviesListScreenListType.UPCOMING
    };

    private static final int[] PAGE_TITLES_MOVIES = {
            R.string.now_playing,
            R.string.popular,
            R.string.top_rated,
            R.string.upcoming
    };

    private MainScreenContract.View view;

    @Inject EventBus eventBus;
    @Inject PreferencesStore preferencesStore;
    @Inject Analytics analytics;

    int currentPage = 0;
    long currentPageStartViewTime = 0;

    @Override
    public void onCreate(ApplicationGraph graph, MainScreenContract.View view) {
        this.view = view;

        graph.inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_about:
                view.showAboutScreen();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPrepareOptionsMenu() {
        view.setToolbarTitle(R.string.movies);
    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onResume() {
        setCurrentPage(view.getCurrentPage());
    }

    @Override
    public void onPause() {
        reportPageViewTime();
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
    }

    @Override
    public void onSaveInstanceState(PresenterSaver saver) {

    }

    @Override
    public void onDestroy() {
        this.view = null;
        this.eventBus = null;
    }

    @Override
    public void onPageSelected(int page) {
        reportPageViewTime();
        setCurrentPage(page);
    }

    @Override
    public int getPageCount() {
        return PAGE_TYPES_MOVIES.length;
    }

    @Override
    public MoviesListScreenListType getPageType(int page) {
        return PAGE_TYPES_MOVIES[page];
    }

    @Override
    public int getPageTitle(int page) {
        return PAGE_TITLES_MOVIES[page];
    }

    @Override
    public void onEvent(MoviesListScreenEvents.OnMovieSelect event) {
        this.view.showMovieSummaryScreen(event.movie);
    }

    @Override
    public void onEvent(MovieSummaryScreenEvents.OnMovieSummaryScreenOpenAnimation event) {
        this.view.blurContainerPrimary(event.duration);
        reportPageViewTime();
    }

    @Override
    public void onEvent(MovieSummaryScreenEvents.OnMovieSummaryScreenExitAnimation event) {
        this.view.deBlurContainerPrimary(event.duration);
        setCurrentPage(view.getCurrentPage());
    }

    private void setCurrentPage(int page) {
        currentPage = page;
        currentPageStartViewTime = System.currentTimeMillis();
    }

    private void reportPageViewTime() {
        long timeOnPage = System.currentTimeMillis() - currentPageStartViewTime;
        analytics.onMoviesListPageChanged(PAGE_TYPES_MOVIES[currentPage].toString(), timeOnPage, TimeUnit.MILLISECONDS);
    }

}
