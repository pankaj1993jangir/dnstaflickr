package com.pj.dnstaflickr.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pj.dnstaflickr.R
import com.pj.dnstaflickr.databinding.ActivityMainBinding
import com.pj.dnstaflickr.presentation.viewmodel.PhotoViewModel


class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: PhotoViewModel
    private var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this@PhotoActivity).get(PhotoViewModel::class.java)
        setPhotosRecyclerView()
        setObservers()
        FetchPhotosAndSetPagination()
    }

    fun setPhotosRecyclerView() {
        val adapter = PhotoAdapter(this)
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.rvPhotos.setLayoutManager(layoutManager)
        binding.rvPhotos.setAdapter(adapter)
        val dividerItemDecoration =
            DividerItemDecoration(binding.rvPhotos.getContext(), LinearLayoutManager.VERTICAL)
        binding.rvPhotos.addItemDecoration(dividerItemDecoration)
        viewmodel.latestPhotoListLive.observe(this, Observer {
            adapter.addOrAppendItems(it)
        })
        viewmodel.cachedPhotoListLive.observe(this, {
            adapter.updateItems(it)
        })
    }

    fun setObservers() {
        viewmodel.itemClick.observe(this, {
            openPhotoDetailFragment(PhotoDetailFragment.newInstance(it.url, it.title))
        })
    }

    fun openPhotoDetailFragment(fragment: Fragment) {
        if (currentFragment != null) {
            supportFragmentManager.beginTransaction().remove(currentFragment as Fragment).commit()
        }
        supportFragmentManager.beginTransaction().replace(binding.fragment.id, fragment)
            .addToBackStack(null).commit()
        binding.fragment.visibility = View.VISIBLE
        currentFragment = fragment
    }

    fun FetchPhotosAndSetPagination() {
        viewmodel.fetchRepoList()
        binding.nestedSv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.pbLoader.visibility = View.VISIBLE
                viewmodel.fetchRepoList()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binding.fragment.visibility = View.GONE
    }
}