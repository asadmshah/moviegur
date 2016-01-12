package com.asadmshah.moviegur.screens.main;

import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.injection.components.MockApplicationGraph;
import com.asadmshah.moviegur.injection.components.MockApplicationGraphFactory;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.models.tmdb.MoviesListResponse;
import com.asadmshah.moviegur.screens.movie_summary.MovieSummaryScreenEvents;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenEvents;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenListType;
import com.asadmshah.moviegur.utils.PresenterSaver;
import com.asadmshah.moviegur.utils.TestResourceLoader;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.common.collect.Range;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import rx.plugins.RxRunOnImmediateThreadRule;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainScreenPresenterUnitTest {

    @Rule public RxRunOnImmediateThreadRule rxRunOnImmediateThreadRule = new RxRunOnImmediateThreadRule();

    @Mock private MainScreenContract.View view;
    @Mock private PresenterSaver saver;

    @Captor private ArgumentCaptor<Integer> toolbarTitleStringResCaptor;
    @Captor private ArgumentCaptor<Long> durationCaptor;
    @Captor private ArgumentCaptor<MoviesList.Movie> movieCaptor;
    @Captor private ArgumentCaptor<Object> objectCaptor;
    @Captor private ArgumentCaptor<String> pageNameCaptor;
    @Captor private ArgumentCaptor<TimeUnit> timeUnitCaptor;

    private MockApplicationGraph mockGraph;
    private MainScreenPresenter presenter = new MainScreenPresenter();

    @Before
    public void setUp() {
        mockGraph = MockApplicationGraphFactory.create();
        presenter = new MainScreenPresenter();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void onCreate_shouldDoNothing() {
        presenter.onCreate(mockGraph, view);

        verifyZeroInteractions(view);
    }

    @Test
    public void onPrepareOptionsMenu_shouldSetToolbarTitle() {
        presenter.onCreate(mockGraph, view);
        presenter.onPrepareOptionsMenu();

        verify(view).setToolbarTitle(toolbarTitleStringResCaptor.capture());

        assertThat(toolbarTitleStringResCaptor.getValue())
                .isEqualTo(R.string.movies);
    }

    @Test
    public void onOptionsItemSelected_shouldShowAboutScreen() {
        presenter.onCreate(mockGraph, view);
        presenter.onPrepareOptionsMenu();
        assertThat(presenter.onOptionsItemSelected(R.id.action_about)).isTrue();

        verify(view).showAboutScreen();
    }

    @Test
    public void onOptionsItemSelected_shouldDoNothing() {
        presenter.onCreate(mockGraph, view);
        presenter.onPrepareOptionsMenu();
        assertThat(presenter.onOptionsItemSelected(0)).isFalse();

        verify(view, never()).showAboutScreen();
    }

    @Test
    public void onStart_shouldRegisterEventBus() throws IOException {
        presenter.onCreate(mockGraph, view);
        presenter.onStart();

        verify(mockGraph.eventBus()).register(objectCaptor.capture());

        assertThat(objectCaptor.getValue())
                .isEqualTo(presenter);
    }

    @Test
    public void onResumeOnPause_shouldReportPageViewTime() throws Exception {
        int expectPage = 0;
        long expectPageDuration = 1000;

        when(view.getCurrentPage()).thenReturn(expectPage);

        presenter.onCreate(mockGraph, view);
        presenter.onResume();
        Thread.sleep(expectPageDuration);
        presenter.onPause();

        verify(mockGraph.analytics()).onMoviesListPageChanged(pageNameCaptor.capture(), durationCaptor.capture(), timeUnitCaptor.capture());

        assertThat(pageNameCaptor.getValue())
                .isEqualTo(presenter.getPageType(expectPage).toString());

        assertThat(durationCaptor.getValue())
                .isIn(Range.open(990L, 1010L));

        assertThat(timeUnitCaptor.getValue())
                .isEqualTo(TimeUnit.MILLISECONDS);
    }

    @Test
    public void onStop_shouldUnregisterEventBus() {
        presenter.onCreate(mockGraph, view);
        presenter.onStop();

        verify(mockGraph.eventBus()).unregister(objectCaptor.capture());

        assertThat(objectCaptor.getValue())
                .isEqualTo(presenter);
    }

    @Test
    public void onSaveInstanceState_shouldDoNothing() {
        presenter.onSaveInstanceState(saver);

        verifyZeroInteractions(view);
    }

    @Test(expected = NullPointerException.class)
    public void onDestroy_shouldRaiseExceptionIfTouchedAfter() {
        presenter.onCreate(mockGraph, view);
        presenter.onDestroy();
        presenter.onEvent(new MoviesListScreenEvents.OnMovieSelect(null));
    }

    @Test
    public void getPageCount_shouldBeFour() {
        assertThat(presenter.getPageCount())
                .isEqualTo(4);
    }

    @Test
    public void getPageType() {
        MoviesListScreenListType expectPageTypes[] = {
                MoviesListScreenListType.NOW_PLAYING,
                MoviesListScreenListType.POPULAR,
                MoviesListScreenListType.TOP_RATED,
                MoviesListScreenListType.UPCOMING
        };

        MoviesListScreenListType resultPageTypes[] = {
            presenter.getPageType(0),
            presenter.getPageType(1),
            presenter.getPageType(2),
            presenter.getPageType(3)
        };

        assertThat(resultPageTypes)
                .isEqualTo(expectPageTypes);
    }

    @Test
    public void getPageTitleResources() {
        int[] expectTitleResources = {
                R.string.now_playing,
                R.string.popular,
                R.string.top_rated,
                R.string.upcoming
        };

        int[] resultTitleResources = {
                presenter.getPageTitle(0),
                presenter.getPageTitle(1),
                presenter.getPageTitle(2),
                presenter.getPageTitle(3)
        };

        assertThat(expectTitleResources)
                .isEqualTo(resultTitleResources);
    }

    @Test
    public void onMovieSelectEvent_shouldShowMovieSummaryScreen() throws IOException {
        MoviesList.Movie movie = generateMoviesList().movies().get(0);

        presenter.onCreate(mockGraph, view);
        presenter.onEvent(new MoviesListScreenEvents.OnMovieSelect(movie));

        verify(view).showMovieSummaryScreen(movieCaptor.capture());

        assertThat(movieCaptor.getValue())
                .isEqualTo(movie);
    }

    @Test
    public void onMovieSummaryScreenOpenAnimationEvent_shouldBlurPrimaryContainer() {
        long expectDuration = 250L;

        presenter.onCreate(mockGraph, view);
        presenter.onEvent(new MovieSummaryScreenEvents.OnMovieSummaryScreenOpenAnimation(expectDuration));

        verify(view).blurContainerPrimary(durationCaptor.capture());

        assertThat(durationCaptor.getValue())
                .isEqualTo(expectDuration);
    }

    @Test
    public void onMovieSummaryScreenExitAnimationEvent_shouldDeBlurPrimaryContainer() {
        long expectDuration = 250L;

        presenter.onCreate(mockGraph, view);
        presenter.onEvent(new MovieSummaryScreenEvents.OnMovieSummaryScreenExitAnimation(expectDuration));

        verify(view).deBlurContainerPrimary(durationCaptor.capture());

        assertThat(durationCaptor.getValue())
                .isEqualTo(expectDuration);
    }

    @Test
    public void onPageChange_shouldSendAnalyticsPageChangeEvent() throws Exception {
        int expectPage = 2;
        long expectPageDuration = 1000;
        TimeUnit expectTimeUnit = TimeUnit.MILLISECONDS;

        when(view.getCurrentPage()).thenReturn(expectPage);

        presenter.onCreate(mockGraph, view);
        presenter.onResume();
        Thread.sleep(expectPageDuration);
        presenter.onPageSelected(3);

        verify(mockGraph.analytics()).onMoviesListPageChanged(pageNameCaptor.capture(), durationCaptor.capture(), timeUnitCaptor.capture());

        assertThat(pageNameCaptor.getValue())
                .isEqualTo(presenter.getPageType(expectPage).toString());

        assertThat(durationCaptor.getValue())
                .isIn(Range.open(990L, 1010L));

        assertThat(timeUnitCaptor.getValue())
                .isEqualTo(expectTimeUnit);
    }

    private MoviesList generateMoviesList() throws IOException {
        InputStream inputStream = TestResourceLoader.load(this, "tmdb_popular_1.json");
        return MoviesList.create(LoganSquare.parse(inputStream, MoviesListResponse.class));
    }

}
