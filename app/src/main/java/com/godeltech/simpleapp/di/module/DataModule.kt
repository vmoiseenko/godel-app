package com.godeltech.simpleapp.di.module

import android.content.Context
import com.godeltech.simpleapp.database.AppDatabase
import com.godeltech.simpleapp.database.dao.HistoryDao
import com.godeltech.simpleapp.network.NetworkService
import com.godeltech.simpleapp.repository.DataRepository
import com.godeltech.simpleapp.repository.DatabaseRepository
import dagger.Module
import dagger.Provides

@Module
open class DataModule {

    @Provides
    fun provideDataRepository(networkService: NetworkService): DataRepository {
        return DataRepository(networkService)
    }

    @Provides
    fun provideHistoryRepository(historyDao: HistoryDao): DatabaseRepository {
        return DatabaseRepository.getInstance(historyDao)
    }

    @Provides
    open fun provideHistoryDao(context: Context): HistoryDao {
        return AppDatabase.getInstance(context.applicationContext).historyDao()
    }

}