package com.pj.dnstaflickr.presentation.view

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pj.dnstaflickr.databinding.ItemFlickrBinding
import com.pj.dnstaflickr.domain.entity.Photo


class PhotoViewHolder(private val binding: ItemFlickrBinding) :
    RecyclerView.ViewHolder(binding.getRoot()) {
    fun setViewModel(item: Photo) {
        binding.title.setText(item.title)
        binding.userName.setText(item.id)
        binding.image.load(item.url)
    }
}