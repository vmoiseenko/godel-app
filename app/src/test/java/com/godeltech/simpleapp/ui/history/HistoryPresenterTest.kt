package com.godeltech.simpleapp.ui.history

import com.godeltech.simpleapp.repository.DatabaseRepository
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations

class HistoryPresenterTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var view: HistoryContact.View

    @Mock
    lateinit var databaseRepository: DatabaseRepository

    lateinit var presenter: HistoryPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = HistoryPresenter(databaseRepository)
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testLoadFromDb() {
        Mockito.`when`(databaseRepository.readHistoryFromDatabase()).thenReturn(Observable.just(arrayListOf()))
        presenter.loadFromDb()
        verify(view).showHistoryData(arrayListOf())
    }

    @Test
    fun testBackMenuItemClick() {
        presenter.onMenuItemClick(android.R.id.home)
        verify(view).onBackButtonClick()
    }

    @Test
    fun testNotBackMenuItemClick() {
        presenter.onMenuItemClick(android.R.id.closeButton)
        verifyNoMoreInteractions(view)
    }
}