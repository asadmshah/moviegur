package com.asadmshah.moviegur.screens.movie_summary;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.injection.components.ApplicationGraphFactory;
import com.asadmshah.moviegur.injection.components.MockApplicationGraph;
import com.asadmshah.moviegur.injection.components.MockApplicationGraphFactory;
import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.models.CastMember;
import com.asadmshah.moviegur.models.CrewMember;
import com.asadmshah.moviegur.models.Movie;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.models.PosterPath;
import com.asadmshah.moviegur.models.Review;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

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
public class MovieSummaryScreenPresenterUnitTest {

    @Rule public RxRunOnImmediateThreadRule rxRunOnImmediateThreadRule = new RxRunOnImmediateThreadRule();

    @Captor private ArgumentCaptor<Movie> movieCaptor;
    @Captor private ArgumentCaptor<String> titleCaptor;
    @Captor private ArgumentCaptor<PosterPath> posterCaptor;
    @Captor private ArgumentCaptor<String> overviewCaptor;
    @Captor private ArgumentCaptor<List<BackdropPath>> backdropListCaptor;
    @Captor private ArgumentCaptor<String> tagLineCaptor;
    @Captor private ArgumentCaptor<Integer> runtimeCaptor;
    @Captor private ArgumentCaptor<Long> releaseDateCaptor;
    @Captor private ArgumentCaptor<String> genresListStringCaptor;
    @Captor private ArgumentCaptor<List<CastMember>> castMembersCaptor;
    @Captor private ArgumentCaptor<List<CrewMember>> crewMembersCaptor;
    @Captor private ArgumentCaptor<List<Review>> reviewsListCaptor;
    @Captor private ArgumentCaptor<Boolean> progressBarEnabledCaptor;
    @Captor private ArgumentCaptor<MovieSummaryScreenEvents.OnMovieSummaryScreenOpenAnimation> screenOpenEventCaptor;
    @Captor private ArgumentCaptor<MovieSummaryScreenEvents.OnMovieSummaryScreenExitAnimation> screenExitEventCaptor;
    @Captor private ArgumentCaptor<Integer> errorStringResCaptor;
    @Captor private ArgumentCaptor<String> restorerKeyCaptor;

    @Mock private MovieSummaryScreenContract.View view;
    @Mock private MovieSummaryScreenContract.EventListener eventListener;
    @Mock private PresenterSaver presenterSaver;
    @Mock private PresenterRestorer presenterRestorer;

    private MoviesList.Movie movie;
    private ApplicationGraph graph;
    private MockApplicationGraph mockGraph;
    private MovieSummaryScreenPresenter presenter;

    @Before
    public void setUp() {
        graph = ApplicationGraphFactory.create(null);
        mockGraph = MockApplicationGraphFactory.create();
        presenter = new MovieSummaryScreenPresenter();
        movie = graph.movieDatabaseClient().getNowPlayingMovies(1).toBlocking().first().movies().get(0);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void onCreate_shouldDoNothing() {
        presenter.onCreate(graph, view, movie);

        verifyZeroInteractions(view);
    }

    @Test
    public void onActivityCreated_shouldSetViewAndRequestMoreMovieData() {
        presenter.onCreate(graph, view, movie);
        presenter.onActivityCreated(null);

        verify(view).setTitle(titleCaptor.capture());
        verify(view).setPoster(posterCaptor.capture());
        verify(view).setOverview(overviewCaptor.capture());
        verify(view).setBackdrops(backdropListCaptor.capture());
        verify(view).setTagLine(tagLineCaptor.capture());
        verify(view).setRuntime(runtimeCaptor.capture());
        verify(view).setReleaseDate(releaseDateCaptor.capture());
        verify(view).setGenres(genresListStringCaptor.capture());
        verify(view).setCastMembers(castMembersCaptor.capture());
        verify(view).setCrewMembers(crewMembersCaptor.capture());
        verify(view).setReviews(reviewsListCaptor.capture());
        verify(view, times(2)).setProgressBarVisibility(progressBarEnabledCaptor.capture());

        // Lazy...
        assertThat(progressBarEnabledCaptor.getAllValues())
                .containsExactly(true, false).inOrder();
    }

    @Test
    public void onActivityCreated_shouldReuseSavedDataIfNotNull() {
        when(presenterRestorer.getParcelable(anyString())).then(new Answer<Movie>() {
            @Override
            public Movie answer(InvocationOnMock invocation) throws Throwable {
                return graph.movieDatabaseClient().getMovie(0).toBlocking().first();
            }
        });

        presenter.onCreate(graph, view, movie);
        presenter.onActivityCreated(presenterRestorer);

        verify(presenterRestorer).getParcelable(anyString());
    }

    @Test
    public void onActivityCreated_shouldRequestDataIfSavedDataIsNull() {
        when(presenterRestorer.getParcelable(anyString())).thenReturn(null);
        presenter.onCreate(graph, view, movie);
        presenter.onActivityCreated(presenterRestorer);

        verify(view).setTitle(titleCaptor.capture());

        assertThat(titleCaptor.getValue())
                .isNotNull();
    }

    @Test
    public void onActivityCreated_shouldRaiseError() {
        when(mockGraph.movieDatabaseClient().getMovie(anyInt())).then(new Answer<Observable>() {
            @Override
            public Observable answer(InvocationOnMock invocation) throws Throwable {
                return Observable.error(new NullPointerException());
            }
        });

        presenter.onCreate(mockGraph, view, movie);
        presenter.onActivityCreated(null);

        verify(view).showError(errorStringResCaptor.capture());

        assertThat(errorStringResCaptor.getValue())
                .isEqualTo(R.string.unable_to_get_movie_data);
    }

    @Test
    public void onSaveInstanceState_shouldSaveDetailedResults() {
        presenter.onCreate(graph, view, movie);
        presenter.onActivityCreated(null);
        presenter.onSaveInstanceState(presenterSaver);

        verify(presenterSaver).putParcelable(anyString(), movieCaptor.capture());

        assertThat(movieCaptor.getValue())
                .isEqualTo(graph.movieDatabaseClient().getMovie(0).toBlocking().first());
    }

    @Test(expected = NullPointerException.class)
    public void onDestroy_shouldRaiseErrorIfTouchedAfter() {
        presenter.onCreate(graph, view, movie);
        presenter.onDestroy();
        presenter.onActivityCreated(null);
    }

    @Test
    public void onOpenAnimation_shouldEmitOpenEvent() {
        long expectDuration = 200;

        graph.eventBus().register(eventListener);
        presenter.onCreate(graph, view, movie);
        presenter.onOpenAnimation(expectDuration);
        graph.eventBus().unregister(eventListener);

        verify(eventListener).onEvent(screenOpenEventCaptor.capture());

        assertThat(screenOpenEventCaptor.getValue().duration)
                .isEqualTo(expectDuration);
    }

    @Test
    public void onExitAnimation_shouldEmitCloseEvent() {
        long expectDuration = 200;

        graph.eventBus().register(eventListener);
        presenter.onCreate(graph, view, movie);
        presenter.onExitAnimation(expectDuration);
        graph.eventBus().unregister(eventListener);

        verify(eventListener).onEvent(screenExitEventCaptor.capture());

        assertThat(screenExitEventCaptor.getValue().duration)
                .isEqualTo(expectDuration);
    }

}
