package com.asadmshah.moviegur.screens.movies_list;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.eventbus.EventBus;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.utils.PresenterRestorer;
import com.asadmshah.moviegur.utils.PresenterSaver;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MoviesListScreenPresenter implements MoviesListScreenContract.Presenter {

    private static final String KEY_MOVIES_LIST = "movies_list";
    private static final String KEY_PREVIOUS_RESPONSE = "previous_response";
    private static final String KEY_SCROLL_POSITION = "scroll_position";

    private MoviesListScreenContract.View view;
    private MoviesListScreenListType type;

    private MoviesList previousMoviesResponse;
    private ArrayList<MoviesList.Movie> moviesList = new ArrayList<>();
    private Subscription subscription;

    @Inject MovieDatabaseClient movieDatabaseClient;
    @Inject
    EventBus eventBus;

    @Override
    public void onCreate(ApplicationGraph graph, MoviesListScreenContract.View view, MoviesListScreenListType type, PresenterRestorer restorer) {
        this.view = view;
        this.type = type;
        graph.inject(this);
    }

    @Override
    public void onActivityCreated(PresenterRestorer restorer) {
        if (restorer == null) {
            getNextListOfMovies();
        } else {
            previousMoviesResponse = restorer.getParcelable(KEY_PREVIOUS_RESPONSE);
            moviesList = restorer.getParcelableArrayList(KEY_MOVIES_LIST);
            if (previousMoviesResponse != null && moviesList != null) {
                notifyDataSetChanged();
                view.setScrollPosition(restorer.getInt(KEY_SCROLL_POSITION));
            }
        }
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        view = null;
        movieDatabaseClient = null;
        eventBus = null;
    }

    @Override
    public void onSaveInstanceState(PresenterSaver saver) {
        saver.putParcelableArrayList(KEY_MOVIES_LIST, moviesList);
        saver.putParcelable(KEY_PREVIOUS_RESPONSE, previousMoviesResponse);
        saver.putInt(KEY_SCROLL_POSITION, view.getScrollPosition());
    }

    @Override
    public int getMoviesCount() {
        return moviesList.size();
    }

    @Override
    public void onBindViewHolder(MoviesListScreenContract.MovieItemViewHolder vh, int position) {
        vh.setTitle(moviesList.get(position).title());
        vh.setBackdrop(moviesList.get(position).backdropPath());
    }

    @Override
    public void onListItemClicked(int position) {
        eventBus.post(new MoviesListScreenEvents.OnMovieSelect(moviesList.get(position)));
    }

    @Override
    public void onScrolledToBottom() {
        getNextListOfMovies();
    }

    private void getNextListOfMovies() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            return;
        }

        int page = 1;
        if (previousMoviesResponse != null) {
            if (previousMoviesResponse.page() == previousMoviesResponse.totalPages()) {
                return;
            }
            page = previousMoviesResponse.page()+1;
        }

        subscription = getMoviesListObservable(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseData, this::handleResponseError);
    }

    private Observable<MoviesList> getMoviesListObservable(int page) {
        Observable<MoviesList> observable = null;
        switch (type) {
            case NOW_PLAYING:
                observable = movieDatabaseClient.getNowPlayingMovies(page);
                break;
            case POPULAR:
                observable = movieDatabaseClient.getPopularMovies(page);
                break;
            case TOP_RATED:
                observable = movieDatabaseClient.getTopRatedMovies(page);
                break;
            case UPCOMING:
                observable = movieDatabaseClient.getUpcomingMovies(page);
                break;
        }
        return observable;
    }

    private void handleResponseData(MoviesList response) {
        previousMoviesResponse = response;
        moviesList.addAll(response.movies());
        notifyDataSetChanged();
    }

    private void handleResponseError(Throwable throwable) {
        Timber.e(throwable, "Could not retrieve movies list");
        view.showError(R.string.unable_to_load_movies);
    }

    private void notifyDataSetChanged() {
        int positionStart = moviesList.size() - previousMoviesResponse.movies().size();
        if (positionStart == 0) {
            view.notifyDataSetChanged();
        } else {
            view.notifyDataSetChanged(positionStart, previousMoviesResponse.movies().size());
        }
    }

}
