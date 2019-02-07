package com.godeltech.simpleapp.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.database.entity.History
import com.godeltech.simpleapp.di.component.DaggerHistoryActivityComponent
import com.godeltech.simpleapp.di.module.HistoryActivityModule
import com.godeltech.simpleapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_history.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HistoryActivity : BaseActivity(), HistoryContact.View {

    @Inject
    lateinit var presenter: HistoryContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.attachView(this)
        presenter.loadFromDb()
    }

    override fun injectDependency() {
        val baseApplication = applicationContext as BaseApplication

        val activityComponent = DaggerHistoryActivityComponent.builder()
            .historyActivityModule(HistoryActivityModule(this))
            .appComponent(baseApplication.getAppComponent())
            .build()

        activityComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.itemId?.let { presenter.onMenuItemClick(it) }
        return true
    }

    override fun showHistoryData(list: List<History>) {
        val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.US)
        list.forEach {
            historyView.append(it.url + " - " + format.format(it.date))
            historyView.append("\n\n")
        }
    }

    override fun onBackButtonClick() {
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}
