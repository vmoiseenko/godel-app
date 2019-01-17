package com.godeltech.simpleapp.repository

import com.godeltech.simpleapp.network.NetworkService

object DataRepositoryProvider {

    fun provideDataRepository(): DataRepository {
        return DataRepository(networkService = NetworkService.instance)
    }

}