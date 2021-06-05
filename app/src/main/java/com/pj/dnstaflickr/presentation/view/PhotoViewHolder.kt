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
        binding.image.load(generateImageUrl(item.farm, item.server, item.id, item.secret))
    }


    fun generateImageUrl(farm: Int, server: String, id: String, secret: String): String {
        return "https://farm$farm.staticflickr.com/$server/$id" + "_" + secret + "_m.jpg"
    }
}