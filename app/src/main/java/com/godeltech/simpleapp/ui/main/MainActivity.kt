package com.godeltech.simpleapp.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.godeltech.simpleapp.BaseApplication
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.di.component.DaggerMainActivityComponent
import com.godeltech.simpleapp.di.module.MainActivityModule
import com.godeltech.simpleapp.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        resultListRecycler.adapter = SimpleRecyclerAdapter()

        urlField.addTextChangedListener(object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onUrlTextChanged(s.toString())
            }
        })

        actionButton.setOnClickListener { presenter.onActionButtonClick() }

        presenter.attach(this)
    }

    private fun injectDependency() {

        val baseApplication = applicationContext as BaseApplication

        val activityComponent = DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .appComponent(baseApplication.getAppComponent())
            .build()

        activityComponent.inject(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
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
