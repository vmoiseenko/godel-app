package com.godeltech.simpleapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.godeltech.simpleapp.database.entity.History
import io.reactivex.Observable

@Dao
interface HistoryDao {

    @Insert
    fun insert(history: History)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<History>)

    @Query("SELECT * FROM history")
    fun getHistory(): Observable<List<History>>

}