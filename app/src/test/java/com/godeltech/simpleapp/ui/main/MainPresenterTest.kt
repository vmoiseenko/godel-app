package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.utils.Validator
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import org.junit.Rule



class MainPresenterTest {

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var view: MainContract.View

    @Mock
    lateinit var interactor: MainInteractor

    @Mock
    lateinit var validator: Validator

    lateinit var presenter: MainPresenter

    val validUrl = "https://google.com"
    val invalidUrl = "https://google"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(interactor, validator)
        presenter.attachView(view)
    }

    @After
    fun terDown() {
        verifyNoMoreInteractions(validator)
        verifyNoMoreInteractions(interactor)
        verifyNoMoreInteractions(view)
    }

    @Test
    fun onInvalidUrlVerifyViewDisabled() {
        Mockito.`when`(validator.isUrlValid(Matchers.anyString())).thenReturn(false)
        presenter.onUrlTextChanged(invalidUrl)

        verify(validator).isUrlValid(invalidUrl)
        verify(view).setActionButtonEnabled(false)
    }

    @Test
    fun onValidUrlVerifyViewEnabled() {
        Mockito.`when`(validator.isUrlValid(validUrl)).thenReturn(true)
        presenter.onUrlTextChanged(validUrl)

        verify(validator).isUrlValid(validUrl)
        verify(interactor).setUrl(validUrl)
        verify(view).setActionButtonEnabled(true)
    }

    @Test
    fun testDisabledUIOnActionButtonClick() {
        Mockito.`when`(validator.isUrlValid(Matchers.anyString())).thenReturn(true)
        Mockito.`when`(interactor.requestData()).thenReturn(PublishSubject.create())

        presenter.onUrlTextChanged(validUrl)

        verify(validator).isUrlValid(validUrl)
        verify(interactor).setUrl(validUrl)
        verify(view).setActionButtonEnabled(true)

        presenter.onActionButtonClick()

        verify(interactor).requestData()
        verifyShowProgress()
    }

    @Test
    fun testHandleSuccessResult() {

        val list = mutableListOf<Pair<String, Int>>()
        list.add(Pair("First", 1))

        Mockito.`when`(interactor.requestData()).thenReturn(Observable.just(list))

        presenter.onActionButtonClick()

        verify(interactor).requestData()
        verifyShowProgress()
        verify(view).addListData(list)
        verifyHideProgress()
    }

    @Test
    fun testHandleErrorResult() {

        val throwable = Throwable("error message test")

        Mockito.`when`(interactor.requestData()).thenReturn(Observable.error(throwable))

        presenter.onActionButtonClick()

        verify(interactor).requestData()
        verifyShowProgress()
        verify(view).showError(throwable)
        verifyHideProgress()
    }

    private fun verifyShowProgress() {
        verify(view).showProgress()
        verify(view).setUrlTextFieldEnabled(false)
        verify(view).setActionButtonEnabled(false)
    }

    private fun verifyHideProgress() {
        verify(view).hideProgress()
        verify(view).setUrlTextFieldEnabled(true)
        verify(view).setActionButtonEnabled(true)
    }

}