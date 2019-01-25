package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.ui.base.BaseContract
import com.godeltech.simpleapp.utils.Validator

class MainContract {

    interface View : BaseContract.View {
        fun showProgress()
        fun hideProgress()
        fun setUrlTextFieldEnabled(isEnabled: Boolean)
        fun setActionButtonEnabled(isEnabled: Boolean)
        fun addListData(list: List<Pair<String, Int>>)
        fun showError(t: Throwable)
    }

    interface Presenter : BaseContract.Presenter<MainContract.View> {
        fun onActionButtonClick()
        fun onUrlTextChanged(url: String)
    }
}