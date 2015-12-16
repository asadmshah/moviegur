package com.asadmshah.moviegur.screens.movies_list;

import android.support.annotation.StringRes;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.utils.PresenterRestorer;
import com.asadmshah.moviegur.utils.PresenterSaveable;

public interface MoviesListScreenContract {

    interface View {

        void notifyDataSetChanged();

        void notifyDataSetChanged(int positionStart, int additionCount);

        int getScrollPosition();

        void setScrollPosition(int position);

        void showError(@StringRes int stringResId);
    }

    interface Presenter extends MoviesDataSource, PresenterSaveable {

        void onCreate(ApplicationGraph graph, View view, MoviesListScreenListType type, PresenterRestorer restorer);

        void onActivityCreated(PresenterRestorer restorer);

        void onDestroy();

        void onListItemClicked(int position);

        void onScrolledToBottom();
    }

    interface EventListener {

        void onEvent(MoviesListScreenEvents.OnMovieSelect event);
    }

    interface MoviesDataSource {

        int getMoviesCount();

        void onBindViewHolder(MovieItemViewHolder vh, int position);
    }

    interface MovieItemViewHolder {

        void setTitle(String title);

        void setBackdrop(BackdropPath backdrop);
    }

}
