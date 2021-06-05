package com.pj.dnstaflickr.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pj.dnstaflickr.R
import com.pj.dnstaflickr.databinding.ActivityMainBinding
import com.pj.dnstaflickr.presentation.viewmodel.PhotoViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: PhotoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this@MainActivity).get(PhotoViewModel::class.java)
        setRecyclerView()
    }

    fun setRecyclerView() {
        val adapter = PhotoAdapter()
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.rvPhotos.setLayoutManager(layoutManager)
        binding.rvPhotos.setAdapter(adapter)
        val dividerItemDecoration =
            DividerItemDecoration(binding.rvPhotos.getContext(), LinearLayoutManager.VERTICAL)
        binding.rvPhotos.addItemDecoration(dividerItemDecoration)
        viewmodel.photoListLive.observe(this, Observer {
            adapter.addItems(it)
        })
        viewmodel.fetchRepoList()
        binding.nestedSv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.pbLoader.visibility = View.VISIBLE
                viewmodel.fetchRepoList()
            }
        })
    }
}