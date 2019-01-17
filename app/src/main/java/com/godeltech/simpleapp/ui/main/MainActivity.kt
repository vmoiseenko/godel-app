package com.godeltech.simpleapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter

    private lateinit var recyclerAdapter: SimpleRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()

        recyclerAdapter = SimpleRecyclerAdapter()
        resultListRecycler.adapter = recyclerAdapter

        urlField.addTextChangedListener(object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onUrlTextChanged(s.toString())
            }
        })

        actionButton.setOnClickListener { presenter.onActionButtonClick() }

        presenter.attach(this)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun addListData(list: List<Pair<String, Int>>) {
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
