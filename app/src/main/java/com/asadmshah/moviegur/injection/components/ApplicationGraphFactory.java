package com.asadmshah.moviegur.injection.components;

import com.asadmshah.moviegur.MoviegurApplication;
import com.asadmshah.moviegur.injection.modules.AnalyticsModule;
import com.asadmshah.moviegur.injection.modules.EventBusModule;
import com.asadmshah.moviegur.injection.modules.MovieDatabaseModule;
import com.asadmshah.moviegur.injection.modules.MoviegurApplicationModule;
import com.asadmshah.moviegur.injection.modules.NetworkModule;
import com.asadmshah.moviegur.injection.modules.PreferencesModule;
import com.asadmshah.moviegur.injection.modules.ResourceSupplierModule;

public final class ApplicationGraphFactory {

    public static ApplicationGraph create(MoviegurApplication application) {
        return DaggerApplicationGraph.builder()
                .moviegurApplicationModule(new MoviegurApplicationModule(application))
                .preferencesModule(new PreferencesModule())
                .eventBusModule(new EventBusModule())
                .networkModule(new NetworkModule())
                .movieDatabaseModule(new MovieDatabaseModule())
                .analyticsModule(new AnalyticsModule())
                .resourceSupplierModule(new ResourceSupplierModule())
                .build();
    }

    private ApplicationGraphFactory() {}

}
