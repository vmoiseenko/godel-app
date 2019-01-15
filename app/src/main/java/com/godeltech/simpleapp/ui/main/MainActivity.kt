package com.godeltech.simpleapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.View
import android.webkit.URLUtil
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()
        presenter.attach(this)

        url_field.addTextChangedListener(object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setActionButtonState(Patterns.WEB_URL.matcher(s).matches())
            }
        })

        actionButton.setOnClickListener { presenter.onActionButtonClick() }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun getActionUrl(): String? {
        return url_field.text.toString()
    }

    override fun setActionButtonState(isEnabled: Boolean) {
        actionButton.isEnabled = isEnabled
    }
}
