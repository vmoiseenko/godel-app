package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.utils.Validator
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class MainPresenterTest {

    @Mock
    lateinit var view: MainContract.View

    @Mock
    lateinit var interactor: MainInteractor

    @Mock
    lateinit var validator: Validator

    lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(interactor, validator)
        presenter.attachView(view)
    }

    @After
    fun terDown() {
        verifyNoMoreInteractions(view)
    }

    @Test
    fun attachView() {
        MatcherAssert.assertThat(presenter.view, CoreMatchers.notNullValue())
    }

    @Test
    fun detachView() {
        presenter.detachView()
        MatcherAssert.assertThat(presenter.view, CoreMatchers.nullValue())
    }

//    @Test
//    fun onUrlTextChanged() {
//        presenter.onUrlTextChanged("www.godel.com")
//        verify(view,times(1)).setActionButtonEnabled(true)
//    }

    @Test
    fun onActionButtonClick() {
        Mockito.`when`(validator.isUrlValid(Matchers.anyString())).thenReturn(true)
        presenter.onUrlTextChanged("https://google.com")

        Mockito.`when`(interactor.requestData()).thenReturn(PublishSubject.create())

        presenter.onActionButtonClick()
        verify(view, times(1)).showProgress()
    }

    @Test
    fun onGetDataSuccess() {
    }

    @Test
    fun onGetDataError() {
    }

//    @Test
//    fun presenter_test(){
//        Assert.assertTrue(presenter.isUrlValid("www.godel.com"))
//    }

//    @Test
//    fun ptest(){
////        presenter.requestData("        http://25.io/toau/audio/sample.tt)")
////
////
////        verify(view,times(1)).hideProgress()
//    }


}