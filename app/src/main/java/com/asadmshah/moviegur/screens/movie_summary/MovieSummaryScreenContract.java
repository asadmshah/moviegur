package com.asadmshah.moviegur.screens.movie_summary;

import android.support.annotation.StringRes;

import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.models.CastMember;
import com.asadmshah.moviegur.models.CrewMember;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.models.PosterPath;
import com.asadmshah.moviegur.models.Review;
import com.asadmshah.moviegur.utils.PresenterRestorer;
import com.asadmshah.moviegur.utils.PresenterSaveable;

import java.util.List;

public interface MovieSummaryScreenContract {

    interface View {

        void setBackdrops(List<BackdropPath> backdrops);

        void setTitle(String title);

        void setPoster(PosterPath path);

        void setTagLine(String tagLine);

        void setReleaseDate(long releaseDate);

        void setRuntime(int runtime);

        void setOverview(String overview);

        void setGenres(String genres);

        void setCastMembers(List<CastMember> castMembers);

        void setCrewMembers(List<CrewMember> crewMembers);

        void setReviews(List<Review> reviews);

        void setProgressBarVisibility(boolean visible);

        void showError(@StringRes int stringResId);
    }

    interface Presenter extends PresenterSaveable {

        void onCreate(ApplicationGraph graph, View view, MoviesList.Movie movie);

        void onActivityCreated(PresenterRestorer restorer);

        void onDestroy();

        void onOpenAnimation(long duration);

        void onExitAnimation(long duration);
    }

    interface EventListener {

        void onEvent(MovieSummaryScreenEvents.OnMovieSummaryScreenOpenAnimation event);

        void onEvent(MovieSummaryScreenEvents.OnMovieSummaryScreenExitAnimation event);
    }

}
