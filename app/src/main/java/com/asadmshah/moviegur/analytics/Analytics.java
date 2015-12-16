package com.asadmshah.moviegur.analytics;

import java.util.concurrent.TimeUnit;

public interface Analytics {

    void onMoviesListPageChanged(String pageName, long duration, TimeUnit timeUnit);
}
