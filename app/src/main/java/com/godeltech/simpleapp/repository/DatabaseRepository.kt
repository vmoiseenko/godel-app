package com.godeltech.simpleapp.repository

import com.godeltech.simpleapp.database.entity.History
import com.godeltech.simpleapp.database.dao.HistoryDao
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val historyDao: HistoryDao) {

    fun createRecordInDatabase(history: History){
        historyDao.insert(history)
    }

    fun readHistoryFromDatabase() = historyDao.getHistory()

    companion object {

        @Volatile
        private var instance: DatabaseRepository? = null

        fun getInstance(historyDao: HistoryDao) =
                instance ?: synchronized(this) {
                    instance ?: DatabaseRepository(historyDao).also { instance = it }
                }
    }

}
