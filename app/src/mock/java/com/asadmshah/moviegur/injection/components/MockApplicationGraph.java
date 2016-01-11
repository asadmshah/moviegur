package com.asadmshah.moviegur.injection.components;

import com.asadmshah.moviegur.injection.modules.MockAnalyticsModule;
import com.asadmshah.moviegur.injection.modules.MockEventBusModule;
import com.asadmshah.moviegur.injection.modules.MockMovieDatabaseModule;
import com.asadmshah.moviegur.injection.modules.MockPreferencesModule;
import com.asadmshah.moviegur.injection.modules.MockResourceSupplierModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MockPreferencesModule.class,
                MockEventBusModule.class,
                MockMovieDatabaseModule.class,
                MockAnalyticsModule.class,
                MockResourceSupplierModule.class
        }
)
public interface MockApplicationGraph extends ApplicationGraph {
}
