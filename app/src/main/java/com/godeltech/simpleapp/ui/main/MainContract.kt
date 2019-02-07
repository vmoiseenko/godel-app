package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.ui.base.BaseContract

class MainContract {

    interface View : BaseContract.View {
        fun showProgress()
        fun hideProgress()
        fun setUrlTextFieldEnabled(isEnabled: Boolean)
        fun setActionButtonEnabled(isEnabled: Boolean)
        fun addListData(list: List<Pair<String, Int>>)
        fun showError(t: Throwable)
        fun launchHistoryScreen()
    }

    interface Presenter : BaseContract.Presenter<MainContract.View> {
        fun onActionButtonClick()
        fun onMenuItemClick(id: Int)
        fun onUrlTextChanged(url: String)
    }
}