package com.godeltech.simpleapp.ui.history

import com.godeltech.simpleapp.database.entity.History
import com.godeltech.simpleapp.ui.base.BaseContract

class HistoryContact {

    interface View : BaseContract.View {
        fun showHistoryData(list: List<History>)
        fun onBackButtonClick()
    }

    interface Presenter : BaseContract.Presenter<HistoryContact.View> {
        fun loadFromDb()
        fun onMenuItemClick(id: Int)
    }

}