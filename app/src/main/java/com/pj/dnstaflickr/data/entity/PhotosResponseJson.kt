package com.pj.dnstaflickr.data.entity

import java.io.Serializable

data class Photo(
    val id: String, val owner: String, val secret: String,
    val server: String, val farm: Int, val title: String, val ispublic: Int,
    val isfriend: Int, val isfamily: Int
) : Serializable


data class Photos(
    val page: Int, val pages: Int, val perpage: Int,
    val total: Int, val photo: List<Photo>
) : Serializable


data class PhotosResponseJson(
    val photos: Photos,
    val stat: String
) : Serializable