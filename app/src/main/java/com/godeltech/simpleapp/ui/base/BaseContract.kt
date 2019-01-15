package com.godeltech.simpleapp.ui.base

class BaseContract {

    interface View

    interface Presenter<in T>{
        fun attach(view: T)
    }

}