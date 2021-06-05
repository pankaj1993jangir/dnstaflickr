package com.pj.dnstaflickr.data.service

import com.pj.dnstaflickr.data.entity.PhotosResponseJson
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FlickerPhotoService {
    @GET("?")
    fun fetchPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") api_key: String = "062a6c0c49e4de1d78497d13a7dbb360",
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int
    ): Observable<PhotosResponseJson>
}