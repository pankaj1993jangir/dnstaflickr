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
                        photo.id, photo.owner, photo.secret,
                        photo.server, photo.farm, photo.title
                    )
                )
            }
            mutableList
        }
    }
}