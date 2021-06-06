package com.pj.dnstaflickr.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pj.dnstaflickr.R
import com.pj.dnstaflickr.data.service.PreferenceService
import com.pj.dnstaflickr.databinding.ActivityMainBinding
import com.pj.dnstaflickr.presentation.viewmodel.PhotoViewModel

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel: PhotoViewModel
    private var currentFragment: Fragment? = null
    private lateinit var tag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        viewmodel = ViewModelProviders.of(this@PhotoActivity).get(PhotoViewModel::class.java)
        tag = PreferenceService.getInstance(this).getPrevQueryTag()
        setPhotosRecyclerView()
        setObservers()
        FetchPhotosAndSetPagination()
    }

    private fun setPhotosRecyclerView() {
        val adapter = PhotoAdapter(this)
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.rvPhotos.setLayoutManager(layoutManager)
        binding.rvPhotos.setAdapter(adapter)
        val dividerItemDecoration =
            DividerItemDecoration(binding.rvPhotos.getContext(), LinearLayoutManager.VERTICAL)
        binding.rvPhotos.addItemDecoration(dividerItemDecoration)
        viewmodel.paginationPhotoListLive.observe(this, Observer {
            adapter.addOrAppendItems(it)
        })
        viewmodel.replacePhotoListLive.observe(this, {
            adapter.updateItems(it)
        })
    }

    private fun setObservers() {
        viewmodel.itemClick.observe(this, {
            openPhotoDetailFragment(PhotoDetailFragment.newInstance(it.url, it.title))
        })
    }

    private fun openPhotoDetailFragment(fragment: Fragment) {
        if (currentFragment != null) {
            supportFragmentManager.beginTransaction().remove(currentFragment as Fragment).commit()
        }
        supportFragmentManager.beginTransaction().replace(binding.fragment.id, fragment)
            .addToBackStack(null).commit()
        binding.fragment.visibility = View.VISIBLE
        currentFragment = fragment
    }

    private fun FetchPhotosAndSetPagination() {
        viewmodel.fetchRepoList(tag)
        binding.nestedSv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                binding.pbLoader.visibility = View.VISIBLE
                viewmodel.fetchRepoList(tag)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binding.fragment.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search_here)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    tag = it
                    viewmodel.fetchRepoList(tag)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}