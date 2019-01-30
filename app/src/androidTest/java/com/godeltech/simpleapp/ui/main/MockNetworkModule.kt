package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.di.module.NetworkModule
import com.godeltech.simpleapp.network.NetworkService
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import retrofit2.Retrofit

@Module
class MockNetworkModule : NetworkModule() {

    @Provides
    @Singleton
    override fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return MockNetworkService()
    }

}