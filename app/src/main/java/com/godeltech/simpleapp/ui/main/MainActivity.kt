package com.godeltech.simpleapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.di.component.DaggerMainActivityComponent
import com.godeltech.simpleapp.di.module.MainActivityModule
import com.godeltech.simpleapp.ui.base.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultListRecycler.adapter = SimpleRecyclerAdapter()

        urlField
            .textChanges()
            .subscribe { mainPresenter.onUrlTextChanged(it.toString()) }

        actionButton
            .clicks()
            .subscribe { mainPresenter.onActionButtonClick() }

        mainPresenter.attachView(this)
    }

    override fun onDestroy() {
        mainPresenter.detachView()
        super.onDestroy()
    }

    override fun injectDependency() {

        val baseApplication = applicationContext as BaseApplication

        val activityComponent = DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .appComponent(baseApplication.getAppComponent())
            .build()

        activityComponent.inject(this)

    }

    override fun addListData(list: List<Pair<String, Int>>) {
        val recyclerAdapter: SimpleRecyclerAdapter = resultListRecycler.adapter as SimpleRecyclerAdapter
        recyclerAdapter.data.addAll(list)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun setActionButtonEnabled(isEnabled: Boolean) {
        actionButton.isEnabled = isEnabled
    }

    override fun setUrlTextFieldEnabled(isEnabled: Boolean) {
        urlField.isEnabled = isEnabled
    }

    override fun showError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }
}
