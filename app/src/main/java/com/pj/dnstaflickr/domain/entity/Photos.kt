package com.pj.dnstaflickr.domain.entity

import java.io.Serializable

data class Photo(
    val id: String, val owner: String, val secret: String,
    val server: String, val farm: Int, val title: String
) : Serializable


