package com.godeltech.simpleapp.ui.main

import com.godeltech.simpleapp.ui.base.BaseContract

class MainContract{

    interface View: BaseContract.View{
        fun showProgress()
        fun hideProgress()
        fun setTextFieldState(isEnabled: Boolean)
        fun setActionButtonState(isEnabled: Boolean)
        fun updateListData(pair: Pair<String, String>)
        fun getFilePath(): String
        fun onError(t: Throwable)
    }

    interface Presenter: BaseContract.Presenter<MainContract.View>{
        fun isUrlValid(url: String): Boolean
        fun requestData(url: String)
        fun calculateWordsInFile(filePath: String): Int
    }
}