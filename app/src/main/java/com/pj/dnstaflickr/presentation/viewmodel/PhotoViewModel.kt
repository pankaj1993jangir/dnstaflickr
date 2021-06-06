package com.pj.dnstaflickr.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pj.dnstaflickr.data.FlickerRepoImpl
import com.pj.dnstaflickr.data.service.PreferenceService
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotoViewModel(private val app: Application) : BaseViewModel(app) {
    val latestPhotoListLive = MutableLiveData<List<Photo>>()
    val cachedPhotoListLive = MutableLiveData<List<Photo>>()

    val itemClick = MutableLiveData<Photo>()

    var pageCount = 1

    fun fetchRepoList() {
        dataLoading.value = true

        FlickerRepoImpl.getInstance(app.applicationContext).getPhotos("funny", pageCount++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photos ->
                dataLoading.value = false
                empty.value = false
                latestPhotoListLive.value = photos
            },
                { error ->
                    dataLoading.value = false
                    empty.value = false
                    cachedPhotoListLive.value =
                        PreferenceService.getInstance(app.applicationContext).getCachedResponse()
                }
            )
    }


}