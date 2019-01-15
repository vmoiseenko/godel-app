package com.godeltech.simpleapp.ui.main

import android.text.Editable
import com.godeltech.simpleapp.ui.base.BaseContract

class MainContract{

    interface View: BaseContract.View{
        fun showProgress()
        fun hideProgress()
        fun getActionUrl(): String?
        fun setActionButtonState(isEnabled: Boolean)
    }

    interface Presenter: BaseContract.Presenter<MainContract.View>{
        fun onActionButtonClick()
    }
}