package com.godeltech.simpleapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.godeltech.simpleapp.database.entity.History
import com.godeltech.simpleapp.repository.DatabaseRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: DatabaseRepository

    private val history1 = History(1000, "history1")
    private val history2 = History(2000, "history2")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        repository = DatabaseRepository(database.historyDao())
        repository.createRecordInDatabase(history1)
        repository.createRecordInDatabase(history2)

    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testReadFromDatabase() {

        val expectedResult = arrayListOf<History>()
        expectedResult.add(history1)
        expectedResult.add(history2)

        repository.readHistoryFromDatabase().test()
            .assertValueCount(1)
            .assertValues(expectedResult)

    }


}