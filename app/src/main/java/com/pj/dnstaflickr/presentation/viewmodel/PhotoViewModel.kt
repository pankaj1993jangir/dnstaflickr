package com.pj.dnstaflickr.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pj.dnstaflickr.data.FlickerRepoImpl
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotoViewModel : BaseViewModel() {
    val photoListLive = MutableLiveData<List<Photo>>()

    val itemClick = MutableLiveData<Photo>()

    var pageCount = 1

    fun fetchRepoList() {
        dataLoading.value = true

        FlickerRepoImpl.getInstance().getPhotos("funny", pageCount++)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photos ->
                dataLoading.value = false
                empty.value = false
                photoListLive.value = photos
            },
                { error ->
                    dataLoading.value = false
                    empty.value = false
                }
            )
    }


}