package com.asadmshah.moviegur.screens.movies_list;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.injection.components.ApplicationGraphFactory;
import com.asadmshah.moviegur.injection.components.MockApplicationGraph;
import com.asadmshah.moviegur.injection.components.MockApplicationGraphFactory;
import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.utils.PresenterRestorer;
import com.asadmshah.moviegur.utils.PresenterSaver;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import rx.Observable;
import rx.plugins.RxRunOnImmediateThreadRule;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoviesListScreenPresenterUnitTest {

    @Rule public RxRunOnImmediateThreadRule rxRunOnImmediateThreadRule = new RxRunOnImmediateThreadRule();

    @Captor private ArgumentCaptor<MoviesListScreenEvents.OnMovieSelect> onMovieSelectEventCaptor;
    @Captor private ArgumentCaptor<MoviesList> moviesListCaptor;
    @Captor private ArgumentCaptor<ArrayList<MoviesList.Movie>> listOfMoviesCaptor;
    @Captor private ArgumentCaptor<Movie> movieCaptor;
    @Captor private ArgumentCaptor<Integer> scrollPositionCaptor;
    @Captor private ArgumentCaptor<Integer> positionStartCaptor;
    @Captor private ArgumentCaptor<Integer> additionCountCaptor;
    @Captor private ArgumentCaptor<String> titleCaptor;
    @Captor private ArgumentCaptor<BackdropPath> backdropPathCaptor;
    @Captor private ArgumentCaptor<Integer> errorStringResCaptor;

    @Mock private MoviesListScreenContract.View view;
    @Mock private MoviesListScreenContract.EventListener eventListener;
    @Mock private MoviesListScreenContract.MovieItemViewHolder movieItemViewHolder;

    private ApplicationGraph graph;
    private MockApplicationGraph mockGraph;
    private MoviesListScreenPresenter presenter;

    @Before
    public void setUp() {
        graph = ApplicationGraphFactory.create(null);
        graph.eventBus().register(eventListener);
        mockGraph = MockApplicationGraphFactory.create();

        presenter = new MoviesListScreenPresenter();
    }

    @After
    public void tearDown() {
        graph.eventBus().unregister(eventListener);
    }

    @Test
    public void onCreate_shouldDoNothing() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);

        verifyZeroInteractions(view);
    }

    @Test
    public void onActivityCreated_withNoSavedData_shouldCallNotifyDataSetChanged() {
        int expectMoviesCount = 18;

        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);

        verify(view).notifyDataSetChanged();

        assertThat(presenter.getMoviesCount())
                .isEqualTo(expectMoviesCount);
    }

    @Test
    public void onActivityCreated_withSavedData_shouldCallNotifyDataSetChanged() {
        int expectScrollPosition = 10;

        PresenterRestorer restorer = Mockito.mock(PresenterRestorer.class);
        when(restorer.getInt(anyString())).thenReturn(expectScrollPosition);
        when(restorer.getParcelable(anyString())).thenReturn(getMockMoviesList(graph.movieDatabaseClient(), 1));
        when(restorer.getParcelableArrayList(anyString())).then(invocation -> getMockListOfMovies(graph.movieDatabaseClient(), 1));

        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(restorer);

        verify(view).notifyDataSetChanged();
        verify(view).setScrollPosition(scrollPositionCaptor.capture());

        assertThat(scrollPositionCaptor.getValue())
                .isEqualTo(expectScrollPosition);
    }

    @Test
    public void onActivityCreated_shouldRequestNowPlayingMovies() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onListItemClicked(0);

        verify(eventListener).onEvent(onMovieSelectEventCaptor.capture());

        assertThat(onMovieSelectEventCaptor.getValue().movie)
                .isEqualTo(graph.movieDatabaseClient().getNowPlayingMovies(1).toBlocking().first().movies().get(0));
    }

    @Test
    public void onActivityCreated_shouldRequestPopularMovies() {
        presenter.onCreate(graph, view, MoviesListScreenListType.POPULAR, null);
        presenter.onActivityCreated(null);
        presenter.onListItemClicked(0);

        verify(eventListener).onEvent(onMovieSelectEventCaptor.capture());

        assertThat(onMovieSelectEventCaptor.getValue().movie)
                .isEqualTo(graph.movieDatabaseClient().getPopularMovies(1).toBlocking().first().movies().get(0));
    }

    @Test
    public void onActivityCreated_shouldRequestTopRatedMovies() {
        presenter.onCreate(graph, view, MoviesListScreenListType.TOP_RATED, null);
        presenter.onActivityCreated(null);
        presenter.onListItemClicked(0);

        verify(eventListener).onEvent(onMovieSelectEventCaptor.capture());

        assertThat(onMovieSelectEventCaptor.getValue().movie)
                .isEqualTo(graph.movieDatabaseClient().getTopRatedMovies(1).toBlocking().first().movies().get(0));
    }

    @Test
    public void onActivityCreated_shouldRequestUpcomingMovies() {
        presenter.onCreate(graph, view, MoviesListScreenListType.UPCOMING, null);
        presenter.onActivityCreated(null);
        presenter.onListItemClicked(0);

        verify(eventListener).onEvent(onMovieSelectEventCaptor.capture());

        assertThat(onMovieSelectEventCaptor.getValue().movie)
                .isEqualTo(graph.movieDatabaseClient().getUpcomingMovies(1).toBlocking().first().movies().get(0));
    }

    @Test
    public void onActivityCreated_shouldHandleError() {
        when(mockGraph.movieDatabaseClient().getNowPlayingMovies(anyInt())).then(new Answer<Observable>() {
            @Override
            public Observable answer(InvocationOnMock invocation) throws Throwable {
                return Observable.error(new NullPointerException());
            }
        });

        presenter.onCreate(mockGraph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);

        verify(view).showError(errorStringResCaptor.capture());

        assertThat(errorStringResCaptor.getValue())
                .isEqualTo(R.string.unable_to_load_movies);
    }

    @Test
    public void onSaveInstanceState_shouldSavePresenterData() {
        int expectScrollPosition = 10;
        when(view.getScrollPosition()).thenReturn(10);

        PresenterSaver saver = Mockito.mock(PresenterSaver.class);

        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onSaveInstanceState(saver);

        verify(view).getScrollPosition();

        verify(saver).putParcelable(anyString(), moviesListCaptor.capture());
        verify(saver).putParcelableArrayList(anyString(), listOfMoviesCaptor.capture());
        verify(saver).putInt(anyString(), scrollPositionCaptor.capture());

        assertThat(moviesListCaptor.getValue())
                .isEqualTo(getMockMoviesList(graph.movieDatabaseClient(), 1));

        assertThat(listOfMoviesCaptor.getValue())
                .isEqualTo(getMockListOfMovies(graph.movieDatabaseClient(), 1));

        assertThat(scrollPositionCaptor.getValue())
                .isEqualTo(expectScrollPosition);
    }

    @Test(expected = NullPointerException.class)
    public void onDestroy_shouldRaiseExceptionsIfTouchedAfter() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onDestroy();
        presenter.onActivityCreated(null);
    }

    @Test
    public void getMoviesCount() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);

        assertThat(presenter.getMoviesCount())
                .isEqualTo(getMockListOfMovies(graph.movieDatabaseClient(), 1).size());
    }

    @Test
    public void onBindViewHolder_shouldSetViewHolderViews() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onBindViewHolder(movieItemViewHolder, 0);

        verify(movieItemViewHolder).setTitle(titleCaptor.capture());
        verify(movieItemViewHolder).setBackdrop(backdropPathCaptor.capture());

        MoviesList moviesList = getMockMoviesList(graph.movieDatabaseClient(), 1);

        assertThat(titleCaptor.getValue())
                .isEqualTo(moviesList.movies().get(0).title());

        assertThat(backdropPathCaptor.getValue())
                .isEqualTo(moviesList.movies().get(0).backdropPath());
    }

    @Test
    public void onListItemClicked_shouldEmitEvent() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onListItemClicked(0);

        verify(eventListener).onEvent(onMovieSelectEventCaptor.capture());

        assertThat(onMovieSelectEventCaptor.getValue().movie)
                .isEqualTo(getMockMoviesList(graph.movieDatabaseClient(), 1).movies().get(0));
    }

    @Test
    public void onScrolledToBottom_shouldRequestMoreData() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onScrolledToBottom();

        verify(view).notifyDataSetChanged(positionStartCaptor.capture(), additionCountCaptor.capture());

        assertThat(positionStartCaptor.getValue())
                .isEqualTo(getMockMoviesList(graph.movieDatabaseClient(), 1).movies().size());

        assertThat(additionCountCaptor.getValue())
                .isEqualTo(getMockMoviesList(graph.movieDatabaseClient(), 2).movies().size());
    }

    @Test
    public void onScrolledToBottom_shouldRequestJustTwice() {
        presenter.onCreate(graph, view, MoviesListScreenListType.NOW_PLAYING, null);
        presenter.onActivityCreated(null);
        presenter.onScrolledToBottom();
        presenter.onScrolledToBottom();

        verify(view, times(1)).notifyDataSetChanged(anyInt(), anyInt());
    }

    private MoviesList getMockMoviesList(MovieDatabaseClient client, int page) {
        return client.getNowPlayingMovies(page).toBlocking().first();
    }

    private ArrayList<MoviesList.Movie> getMockListOfMovies(MovieDatabaseClient client, int page) {
        MoviesList moviesList = getMockMoviesList(client, page);
        ArrayList<MoviesList.Movie> movies = new ArrayList<>(moviesList.movies().size());
        for (MoviesList.Movie movie : moviesList.movies()) {
            movies.add(movie);
        }
        return movies;
    }

}
