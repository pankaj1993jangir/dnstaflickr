package com.pj.dnstaflickr.data.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.flickr.com/services/rest/"

class RetroApiClient {
    var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    var flikrService: FlickerPhotoService = retrofit.create(FlickerPhotoService::class.java)
}