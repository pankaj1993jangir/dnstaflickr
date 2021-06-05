package com.pj.dnstaflickr.data

import com.pj.dnstaflickr.domain.repository.FlickerRepo
import com.pj.dnstaflickr.data.service.FlickerPhotoService
import com.pj.dnstaflickr.data.service.RetroApiClient
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.Observable

class FlickerRepoImpl : FlickerRepo {
    var service: FlickerPhotoService

    init {
        service = RetroApiClient().redMartService
    }

    override fun getPhotos(
        tag: String,
        page: Int
    ): Observable<List<Photo>> {
        return service.fetchPhotos(tags = tag, page = page).map { response ->
            val mutableList: MutableList<Photo> = ArrayList()
            response?.photos?.photo?.map { photo ->
                mutableList.add(
                    Photo(
                        photo.id, generateImageUrl(photo.farm, photo.server, photo.id, photo.secret), photo.title
                    )
                )
            }
            mutableList
        }
    }

    fun generateImageUrl(farm: Int, server: String, id: String, secret: String): String {
        return "https://farm$farm.staticflickr.com/$server/$id" + "_" + secret + "_m.jpg"
    }

    companion object {
        private var INSTANCE: FlickerRepo? = null
        fun getInstance() = INSTANCE
            ?: FlickerRepoImpl().also {
                INSTANCE = it
            }
    }
}