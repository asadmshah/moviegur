package com.asadmshah.moviegur.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenFragment;

class MainScreenPagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;
    private final MainScreenContract.PagerDataSource dataSource;

    public MainScreenPagerAdapter(FragmentManager fm, Context context, MainScreenContract.PagerDataSource dataSource) {
        super(fm);
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public Fragment getItem(int position) {
        return MoviesListScreenFragment.newInstance(dataSource.getPageType(position));
    }

    @Override
    public int getCount() {
        return dataSource.getPageCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(dataSource.getPageTitle(position));
    }

}
