package com.asadmshah.moviegur.screens.movie_summary;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.eventbus.EventBus;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.Genre;
import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.utils.PresenterRestorer;
import com.asadmshah.moviegur.utils.PresenterSaver;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MovieSummaryScreenPresenter implements MovieSummaryScreenContract.Presenter {

    private static final String KEY_MOVIE_DETAILS = "movie_details";

    private MovieSummaryScreenContract.View view;

    private Subscription subscription;

    private MoviesList.Movie movieBasics;
    private Movie movieDetails;

    @Inject MovieDatabaseClient movieDatabaseClient;
    @Inject EventBus eventBus;

    @Override
    public void onCreate(ApplicationGraph graph, MovieSummaryScreenContract.View view, MoviesList.Movie movie) {
        this.view = view;
        this.movieBasics = movie;

        graph.inject(this);
    }

    @Override
    public void onActivityCreated(PresenterRestorer restorer) {
        view.setTitle(movieBasics.title());
        view.setPoster(movieBasics.posterPath());
        view.setOverview(movieBasics.overview());

        if (restorer == null) {
            requestDetailedMovieData();
        } else {
            Movie savedResponse = restorer.getParcelable(KEY_MOVIE_DETAILS);
            if (savedResponse == null) {
                requestDetailedMovieData();
            } else {
                setDetailedMovieData(savedResponse);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        this.subscription = null;
        this.view = null;
        this.movieDatabaseClient = null;
        this.eventBus = null;
    }

    @Override
    public void onSaveInstanceState(PresenterSaver saver) {
        saver.putParcelable(KEY_MOVIE_DETAILS, movieDetails);
    }

    @Override
    public void onOpenAnimation(long duration) {
        this.eventBus.post(new MovieSummaryScreenEvents.OnMovieSummaryScreenOpenAnimation(duration));
    }

    @Override
    public void onExitAnimation(long duration) {
        this.eventBus.post(new MovieSummaryScreenEvents.OnMovieSummaryScreenExitAnimation(duration));
    }

    private void requestDetailedMovieData() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            return;
        }

        view.setProgressBarVisibility(true);
        subscription = movieDatabaseClient.getMovie(movieBasics.id())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setDetailedMovieData, this::handleResponseError);
    }

    private void setDetailedMovieData(Movie movie) {
        this.movieDetails = movie;

        view.setProgressBarVisibility(false);
        view.setBackdrops(movie.images().backdrops());
        if (movie.tagLine() != null) {
            view.setTagLine(movie.tagLine());
        }
        if (movie.runtime() > 0) {
            view.setRuntime(movie.runtime());
        }
        view.setReleaseDate(movie.releaseDate());

        view.setGenres(genresListToString(movie.genres()));

        view.setCastMembers(movie.credits().castMembers());
        view.setCrewMembers(movie.credits().crewMembers());

        view.setReviews(movie.reviews());
    }

    private String genresListToString(List<Genre> genres) {
        StringBuilder sb = new StringBuilder();
        int n = genres.size();
        for (int i = 0; i < n; i++) {
            sb.append(genres.get(i).name());
            if (i + 1 < n) {
                sb.append(',');
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private void handleResponseError(Throwable throwable) {
        Timber.e(throwable, "Unable to get detailed movie data");
        view.setProgressBarVisibility(false);
        view.showError(R.string.unable_to_get_movie_data);
    }

}
