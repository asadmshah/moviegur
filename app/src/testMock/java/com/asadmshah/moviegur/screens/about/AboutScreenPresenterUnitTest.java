package com.asadmshah.moviegur.screens.about;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.injection.components.ApplicationGraphFactory;
import com.asadmshah.moviegur.injection.components.MockApplicationGraph;
import com.asadmshah.moviegur.injection.components.MockApplicationGraphFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.plugins.RxRunOnImmediateThreadRule;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AboutScreenPresenterUnitTest {

    @Rule public RxRunOnImmediateThreadRule rxRunOnImmediateThreadRule = new RxRunOnImmediateThreadRule();

    @Mock AboutScreenContract.View view;
    @Mock AboutScreenContract.LibraryItemViewHolder viewHolder;

    @Captor ArgumentCaptor<String> titleCaptor;
    @Captor ArgumentCaptor<String> descriptionCaptor;
    @Captor ArgumentCaptor<Integer> viewTypeCaptor;

    private ApplicationGraph graph;
    private AboutScreenContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new AboutScreenPresenter();

        graph = ApplicationGraphFactory.create(null);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void onCreate_shouldNotifyDataSetChanged() {
        presenter.onCreate(graph, view);

        verify(view).notifyDataSetChanged();
    }

    @Test
    public void onOptionsItemSelected_shouldNavigateUpFromSameTask() {
        presenter.onCreate(graph, view);
        assertThat(presenter.onOptionsItemSelected(android.R.id.home)).isTrue();

        verify(view).navigateUpFromSameTask();
    }

    @Test
    public void onOptionsItemSelected_shouldDoNothing() {
        presenter.onCreate(graph, view);
        assertThat(presenter.onOptionsItemSelected(0)).isFalse();

        verify(view, never()).navigateUpFromSameTask();
    }

    @Test
    public void getLibrariesCount() {
        presenter.onCreate(graph, view);

        assertThat(presenter.getLibrariesCount())
                .isEqualTo(graph.resourceSupplier().getLibraryTitles().length + 1);
    }

    @Test
    public void getLibraryItemViewType() {
        presenter.onCreate(graph, view);

        assertThat(presenter.getLibraryItemType(0))
                .isEqualTo(AboutScreenContract.LibrariesDataSource.VIEW_TYPE_HEADER_ITEM);

        assertThat(presenter.getLibraryItemType(1))
                .isEqualTo(AboutScreenContract.LibrariesDataSource.VIEW_TYPE_LIBRARY_ITEM);
    }

    @Test
    public void onBindViewHolder() {
        int position1 = 1;
        int position2 = 2;

        presenter.onCreate(graph, view);
        presenter.onBindViewHolder(viewHolder, position1);
        presenter.onBindViewHolder(viewHolder, position2);

        verify(viewHolder, times(2)).setTitle(titleCaptor.capture());
        verify(viewHolder, times(2)).setDescription(descriptionCaptor.capture());

        assertThat(titleCaptor.getAllValues().get(0))
                .isEqualTo(graph.resourceSupplier().getLibraryTitles()[position1-1]);

        assertThat(descriptionCaptor.getAllValues().get(0))
                .isEqualTo(graph.resourceSupplier().getLibraryDescriptions()[position1-1]);

        assertThat(titleCaptor.getAllValues().get(1))
                .isEqualTo(graph.resourceSupplier().getLibraryTitles()[position2-1]);

        assertThat(descriptionCaptor.getAllValues().get(1))
                .isEqualTo(graph.resourceSupplier().getLibraryDescriptions()[position2-1]);
    }

}
