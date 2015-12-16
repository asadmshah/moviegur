package com.asadmshah.moviegur.screens.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.asadmshah.moviegur.MoviegurApplication;
import com.asadmshah.moviegur.R;
import com.asadmshah.moviegur.injection.components.ApplicationGraph;
import com.asadmshah.moviegur.models.MoviesList;
import com.asadmshah.moviegur.screens.movie_summary.MovieSummaryScreenFragment;
import com.asadmshah.moviegur.utils.BundleBackedPresenterSaverRestorer;
import com.asadmshah.moviegur.widgets.BlurView;

public class MainScreenActivity extends AppCompatActivity implements MainScreenContract.View, ViewPager.OnPageChangeListener {

    private MainScreenContract.Presenter presenter = new MainScreenPresenter();

    private View viewContainerPrimary;
    private BlurView viewContainerPrimaryBlur;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationGraph graph = MoviegurApplication.getGraph(this);
        presenter.onCreate(graph, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(" ");
        }

        viewContainerPrimary = findViewById(R.id.container_primary);
        viewContainerPrimaryBlur = (BlurView) findViewById(R.id.container_primary_blur);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainScreenPagerAdapter(getSupportFragmentManager(), this, presenter));
        viewPager.addOnPageChangeListener(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        presenter.onPrepareOptionsMenu();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(new BundleBackedPresenterSaverRestorer(outState));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        viewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void setToolbarTitle(int stringResId) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(stringResId);
        }
    }

    @Override
    public void showMovieSummaryScreen(MoviesList.Movie movie) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_foreground, MovieSummaryScreenFragment.newInstance(movie), null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void blurContainerPrimary(long duration) {
        viewContainerPrimaryBlur.setAlpha(0f);
        viewContainerPrimaryBlur.setVisibility(View.VISIBLE);
        viewContainerPrimaryBlur.setBlurSource(viewContainerPrimary, 10, 20);
        viewContainerPrimaryBlur.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.removeAllListeners();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animation.removeAllListeners();
                    }
                })
                .start();
    }

    @Override
    public void deBlurContainerPrimary(long duration) {
        viewContainerPrimaryBlur.setAlpha(1f);
        viewContainerPrimaryBlur.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.removeAllListeners();
                        viewContainerPrimaryBlur.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animation.removeAllListeners();
                        viewContainerPrimaryBlur.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }

    @Override
    public int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
