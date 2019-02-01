package com.godeltech.simpleapp.ui.base

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BasePresenterTest {

    @Mock
    lateinit var view: BaseContract.View

    lateinit var presenter: BasePresenter<BaseContract.View>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BasePresenter()
        presenter.attachView(view)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(view)
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
}