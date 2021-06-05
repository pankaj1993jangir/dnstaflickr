package com.pj.dnstaflickr.presentation.view

import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.RecyclerView
import com.pj.dnstaflickr.databinding.ItemFlickrBinding
import com.pj.dnstaflickr.domain.entity.Photo
import com.pj.dnstaflickr.presentation.viewmodel.PhotoViewModel
import java.util.ArrayList


class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<PhotoViewHolder>() {
    private var list: MutableList<Photo> = ArrayList()
    private lateinit var viewModel: PhotoViewModel

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        viewModel = ViewModelProviders.of(context as PhotoActivity).get(PhotoViewModel::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding: ItemFlickrBinding =
            ItemFlickrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item: Photo = list[position]
        holder.setViewModel(item)
        holder.itemView.setOnClickListener { view ->
            viewModel.itemClick.value = item
        }
    }

    fun addItems(listItems: List<Photo>) {
        if (list == null) {
            list = ArrayList()
        }
        list.addAll(listItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}