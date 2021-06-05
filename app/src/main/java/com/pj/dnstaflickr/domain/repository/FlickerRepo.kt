package com.pj.dnstaflickr.domain.repository

import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.Observable

interface FlickerRepo {
    fun getPhotos(tag: String, page: Int): Observable<List<Photo>>
}