package com.godeltech.simpleapp.di.module

import com.godeltech.simpleapp.network.NetworkService
import com.godeltech.simpleapp.repository.DataRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDataRepository(networkService: NetworkService): DataRepository {
        return DataRepository(networkService)
    }

}