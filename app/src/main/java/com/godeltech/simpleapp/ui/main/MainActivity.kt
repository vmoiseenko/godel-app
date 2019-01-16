package com.godeltech.simpleapp.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.godeltech.simpleapp.R
import com.godeltech.simpleapp.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter

    private lateinit var recyclerAdapter: SimpleRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()
        presenter.attach(this)

        recyclerAdapter = SimpleRecyclerAdapter(ArrayList())
        resultListRecycler.adapter = recyclerAdapter

        urlField.addTextChangedListener(object : SimpleTextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setActionButtonState(presenter.isUrlValid(s.toString()))
            }
        })

        actionButton.setOnClickListener { presenter.requestData(urlField.text.toString()) }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun updateListData(pair: Pair<String, String>) {
        recyclerAdapter.data.add(pair)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun setActionButtonState(isEnabled: Boolean) {
        actionButton.isEnabled = isEnabled
    }

    override fun setTextFieldState(isEnabled: Boolean) {
        urlField.isEnabled = isEnabled
    }

    override fun getFilePath(): String {
        return getExternalFilesDir(null).toString() + File.separator + "file.txt"
    }

    override fun onError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }
}
