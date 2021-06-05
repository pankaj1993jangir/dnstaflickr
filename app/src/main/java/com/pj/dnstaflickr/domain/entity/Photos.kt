package com.pj.dnstaflickr.domain.entity

import java.io.Serializable

data class Photo(
    val id: String, val url: String,  val title: String
) : Serializable


