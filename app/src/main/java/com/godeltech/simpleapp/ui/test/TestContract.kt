package com.godeltech.simpleapp.ui.test

import com.godeltech.simpleapp.ui.base.BaseContract

interface TestContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View>
}