package com.pj.dnstaflickr.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pj.dnstaflickr.data.FlickerRepoImpl
import com.pj.dnstaflickr.data.service.PreferenceService
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotoViewModel(private val app: Application) : BaseViewModel(app) {
    val paginationPhotoListLive = MutableLiveData<List<Photo>>()
    val replacePhotoListLive = MutableLiveData<List<Photo>>()

    val itemClick = MutableLiveData<Photo>()

    var pageCount = 1
    var preTagQuery = ""
    var newTagQuery = true

    fun fetchRepoList(tag: String = "funny") {
        dataLoading.value = true
        if (!preTagQuery.equals(tag)) {
            preTagQuery = tag
            pageCount = 1
            newTagQuery = true
        }
        FlickerRepoImpl.getInstance(app.applicationContext).getPhotos(tag, pageCount++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photos ->
                dataLoading.value = false
                empty.value = false
                if (newTagQuery) {
                    replacePhotoListLive.value = photos
                    newTagQuery = false
                } else {
                    paginationPhotoListLive.value = photos
                }
            },
                { error ->
                    dataLoading.value = false
                    empty.value = false
                    replacePhotoListLive.value =
                        PreferenceService.getInstance(app.applicationContext).getCachedResponse()
                }
            )
    }


}