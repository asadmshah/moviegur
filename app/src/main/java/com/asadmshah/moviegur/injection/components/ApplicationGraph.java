package com.asadmshah.moviegur.injection.components;

import com.asadmshah.moviegur.analytics.Analytics;
import com.asadmshah.moviegur.eventbus.EventBus;
import com.asadmshah.moviegur.injection.modules.AnalyticsModule;
import com.asadmshah.moviegur.injection.modules.EventBusModule;
import com.asadmshah.moviegur.injection.modules.MovieDatabaseModule;
import com.asadmshah.moviegur.injection.modules.MoviegurApplicationModule;
import com.asadmshah.moviegur.injection.modules.NetworkModule;
import com.asadmshah.moviegur.injection.modules.PreferencesModule;
import com.asadmshah.moviegur.injection.modules.ResourceSupplierModule;
import com.asadmshah.moviegur.network.MovieDatabaseClient;
import com.asadmshah.moviegur.preferences.PreferencesStore;
import com.asadmshah.moviegur.screens.about.AboutScreenPresenter;
import com.asadmshah.moviegur.screens.main.MainScreenPresenter;
import com.asadmshah.moviegur.screens.movie_summary.MovieSummaryScreenPresenter;
import com.asadmshah.moviegur.screens.movies_list.MoviesListScreenPresenter;
import com.asadmshah.moviegur.utils.ResourceSupplier;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MoviegurApplicationModule.class,
                PreferencesModule.class,
                NetworkModule.class,
                EventBusModule.class,
                MovieDatabaseModule.class,
                AnalyticsModule.class,
                ResourceSupplierModule.class
        }
)
public interface ApplicationGraph {
    void inject(MainScreenPresenter presenter);
    void inject(MoviesListScreenPresenter presenter);
    void inject(MovieSummaryScreenPresenter presenter);
    void inject(AboutScreenPresenter presenter);

    PreferencesStore preferencesStore();
    EventBus eventBus();
    MovieDatabaseClient movieDatabaseClient();
    Analytics analytics();
    ResourceSupplier resourceSupplier();
}
