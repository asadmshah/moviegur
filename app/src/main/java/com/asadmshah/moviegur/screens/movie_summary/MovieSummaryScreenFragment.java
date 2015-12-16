package com.asadmshah.moviegur.screens.movie_summary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.asadmshah.moviegur.MoviegurApplication;
import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.BackdropPath;
import com.asadmshah.moviegur.models.CastMember;
import com.asadmshah.moviegur.models.CrewMember;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.models.PosterPath;
import com.asadmshah.moviegur.models.Review;
import com.asadmshah.moviegur.utils.BundleBackedPresenterSaverRestorer;
import com.asadmshah.moviegur.widgets.BackdropViewPager;
import com.asadmshah.moviegur.widgets.CastMemberAdapter;
import com.asadmshah.moviegur.widgets.CrewMemberAdapter;
import com.asadmshah.moviegur.widgets.ReviewsView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieSummaryScreenFragment extends Fragment implements MovieSummaryScreenContract.View {

    private static final String KEY_MOVIE = "movie";

    private MovieSummaryScreenContract.Presenter presenter = new MovieSummaryScreenPresenter();

    private BackdropViewPager viewBackdrops;
    private TextView viewTitle;
    private ImageView viewPoster;
    private ProgressBar viewProgressBar;
    private TextView viewTagLine;
    private TextView viewReleaseDate;
    private TextView viewRuntime;
    private TextView viewOverview;
    private TextView viewGenres;
    private RecyclerView viewCastList;
    private RecyclerView viewCrewList;
    private ReviewsView viewReviews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationGraph graph = MoviegurApplication.getGraph(getActivity());
        MoviesList.Movie movie = getArguments().getParcelable(KEY_MOVIE);
        presenter.onCreate(graph, this, movie);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_summary, container, false);

        viewBackdrops = (BackdropViewPager) view.findViewById(R.id.backdrop_pager);
        viewTitle = (TextView) view.findViewById(R.id.title);
        viewPoster = (ImageView) view.findViewById(R.id.poster);
        viewProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        viewTagLine = (TextView) view.findViewById(R.id.tagline);
        viewReleaseDate = (TextView) view.findViewById(R.id.release_date);
        viewRuntime = (TextView) view.findViewById(R.id.runtime);
        viewOverview = (TextView) view.findViewById(R.id.overview);
        viewGenres = (TextView) view.findViewById(R.id.genres);

        viewCastList = (RecyclerView) view.findViewById(R.id.cast_list);
        viewCastList.setHasFixedSize(true);
        viewCastList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        viewCrewList = (RecyclerView) view.findViewById(R.id.crew_list);
        viewCrewList.setHasFixedSize(true);
        viewCrewList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        viewReviews = (ReviewsView) view.findViewById(R.id.reviews_view);

        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {
        Animation animation;
        if (enter) {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
            animation.setInterpolator(new OvershootInterpolator(0.5f));
            animation.setDuration(350);
            presenter.onOpenAnimation(animation.getDuration());
        } else {
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(200);
            presenter.onExitAnimation(animation.getDuration());
        }
        return animation;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            presenter.onActivityCreated(null);
        } else {
            presenter.onActivityCreated(new BundleBackedPresenterSaverRestorer(savedInstanceState));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(new BundleBackedPresenterSaverRestorer(outState));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void setBackdrops(List<BackdropPath> backdrops) {
        viewBackdrops.setBackdropPaths(backdrops);
    }

    @Override
    public void setTitle(String title) {
        viewTitle.setText(title);
    }

    @Override
    public void setPoster(PosterPath path) {
        Glide.with(this).load(path).fitCenter().into(viewPoster);
    }

    @Override
    public void setProgressBarVisibility(boolean visible) {
        viewProgressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setTagLine(String tagLine) {
        viewTagLine.setText(tagLine);
    }

    @Override
    public void setReleaseDate(long releaseDate) {
        SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        viewReleaseDate.setText(df.format(new Date(releaseDate)));
    }

    @Override
    public void setRuntime(int runtime) {
        viewRuntime.setText(getActivity().getString(R.string.runtime_format, runtime));
    }

    @Override
    public void setOverview(String overview) {
        viewOverview.setText(overview);
    }

    @Override
    public void setGenres(String genres) {
        viewGenres.setText(genres);
    }

    @Override
    public void setCastMembers(List<CastMember> castMembers) {
        viewCastList.setAdapter(new CastMemberAdapter(castMembers));
    }

    @Override
    public void setCrewMembers(List<CrewMember> crewMembers) {
        viewCrewList.setAdapter(new CrewMemberAdapter(crewMembers));
    }

    @Override
    public void setReviews(List<Review> reviews) {
        viewReviews.setReviews(reviews);
    }

    @Override
    public void showError(@StringRes int stringResId) {
        Snackbar.make(getView(), stringResId, Snackbar.LENGTH_LONG).show();
    }

    public static MovieSummaryScreenFragment newInstance(MoviesList.Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_MOVIE, movie);
        MovieSummaryScreenFragment fragment = new MovieSummaryScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
