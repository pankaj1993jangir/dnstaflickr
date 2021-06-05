package com.pj.dnstaflickr.presentation.view

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.pj.dnstaflickr.databinding.ItemFlickrBinding
import com.pj.dnstaflickr.domain.entity.Photo
import java.util.ArrayList


class PhotoAdapter : RecyclerView.Adapter<PhotoViewHolder>() {
    private var list: MutableList<Photo> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding: ItemFlickrBinding =
            ItemFlickrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item: Photo = list[position]
        holder.setViewModel(item)
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