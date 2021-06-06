package com.pj.dnstaflickr.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.pj.dnstaflickr.domain.repository.FlickerRepo
import com.pj.dnstaflickr.data.service.FlickerPhotoService
import com.pj.dnstaflickr.data.service.PreferenceService
import com.pj.dnstaflickr.data.service.RetroApiClient
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.Observable

class FlickerRepoImpl(context: Context) : FlickerRepo {
    private var service: FlickerPhotoService = RetroApiClient().flikrService
    private var preferenceService: PreferenceService = PreferenceService.getInstance(context)

    override fun getPhotos(
        tag: String,
        page: Int
    ): Observable<List<Photo>> {
        return service.fetchPhotos(tags = tag, page = page).map { response ->
            var mutableList: MutableList<Photo> = ArrayList()
            response?.photos?.photo?.map { photo ->
                mutableList.add(
                    Photo(
                        photo.id,
                        generateImageUrl(photo.farm, photo.server, photo.id, photo.secret),
                        photo.title
                    )
                )
            }
            preferenceService.updatePrevQuery(tag)
            preferenceService.updateCachedResponse(mutableList)
            mutableList
        }
    }


    @VisibleForTesting
    fun generateImageUrl(farm: Int, server: String, id: String, secret: String): String {
        return "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}_m.jpg"
    }

    companion object {
        private var INSTANCE: FlickerRepo? = null
        fun getInstance(context: Context) = INSTANCE
            ?: FlickerRepoImpl(context).also {
                INSTANCE = it
            }
    }
}