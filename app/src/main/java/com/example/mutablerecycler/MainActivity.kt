package com.example.mutablerecycler

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainAdapter.OnButtonClickListener {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initRecycler()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(this, Observer {
            mainAdapter.submitList(it.toList())
        })
    }

    private fun initRecycler() {
        mainAdapter = MainAdapter(this)
        recyclerview_main.layoutManager =
            GridLayoutManager(this, calculateColumns(), GridLayoutManager.VERTICAL, false)
        recyclerview_main.adapter = mainAdapter
    }

    override fun onButtonClick(element: Int) {
        viewModel.deleteElement(element)
    }

    private fun calculateColumns(): Int {
        return when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
    }


}