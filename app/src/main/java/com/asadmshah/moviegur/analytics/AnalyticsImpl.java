package com.asadmshah.moviegur.analytics;

import android.content.Context;

import com.asadmshah.moviegur.BuildConfig;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.concurrent.TimeUnit;

public class AnalyticsImpl implements Analytics {

    private static final String CATEGORY_PAGE_VIEW_DURATION = "Page View Duration";

    private final Tracker tracker;

    public AnalyticsImpl(Context context) {
        tracker = GoogleAnalytics.getInstance(context).newTracker(BuildConfig.TRACKING_ID);
        tracker.enableAutoActivityTracking(false);
        tracker.enableExceptionReporting(false);
    }

    @Override
    public void onMoviesListPageChanged(String pageName, long duration, TimeUnit timeUnit) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(CATEGORY_PAGE_VIEW_DURATION)
                .setAction(pageName)
                .setValue(timeUnit.toSeconds(duration))
                .build());
    }
}
