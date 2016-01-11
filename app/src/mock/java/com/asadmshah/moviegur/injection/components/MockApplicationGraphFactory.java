package com.asadmshah.moviegur.injection.components;

import com.asadmshah.moviegur.injection.modules.MockAnalyticsModule;
import com.asadmshah.moviegur.injection.modules.MockEventBusModule;
import com.asadmshah.moviegur.injection.modules.MockMovieDatabaseModule;
import com.asadmshah.moviegur.injection.modules.MockPreferencesModule;
import com.asadmshah.moviegur.injection.modules.MockResourceSupplierModule;

public final class MockApplicationGraphFactory {

    public static MockApplicationGraph create() {
        return DaggerMockApplicationGraph.builder()
                .mockPreferencesModule(new MockPreferencesModule())
                .mockMovieDatabaseModule(new MockMovieDatabaseModule())
                .mockEventBusModule(new MockEventBusModule())
                .mockAnalyticsModule(new MockAnalyticsModule())
                .mockResourceSupplierModule(new MockResourceSupplierModule())
                .build();
    }

    private MockApplicationGraphFactory() {}

}
