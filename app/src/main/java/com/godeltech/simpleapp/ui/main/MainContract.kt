package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.ui.base.BaseContract

class MainContract{
    interface View: BaseContract.View

    interface Presenter: BaseContract.Presenter<MainContract.View>
}