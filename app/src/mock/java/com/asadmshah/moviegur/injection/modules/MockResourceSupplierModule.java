package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.utils.ResourceSupplier;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockResourceSupplierModule {

    @Provides
    @Singleton
    public ResourceSupplier providesResourceSupplier() {
        return Mockito.mock(ResourceSupplier.class);
    }

}
