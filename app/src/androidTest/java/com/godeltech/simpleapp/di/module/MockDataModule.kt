package com.godeltech.simpleapp.di.module

import android.content.Context
import androidx.room.Room
import com.godeltech.simpleapp.database.AppDatabase
import com.godeltech.simpleapp.database.dao.HistoryDao
import dagger.Provides

class MockDataModule : DataModule(){

    @Provides
    override fun provideHistoryDao(context: Context): HistoryDao {
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        return database.historyDao()
    }

}