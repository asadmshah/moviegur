package com.asadmshah.moviegur.injection.modules;

import com.asadmshah.moviegur.utils.ResourceSupplier;
import com.asadmshah.moviegur.utils.ResourceSupplierImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ResourceSupplierModule {

    @Provides
    @Singleton
    public ResourceSupplier providesResourceSupplier() {
        return new ResourceSupplierImpl();
    }

}
