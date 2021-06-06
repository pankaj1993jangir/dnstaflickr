package com.pj.dnstaflickr.data.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pj.dnstaflickr.domain.entity.Photo

private const val SHARED_PREF_STORE = "dnstaflickerStore"
private const val KEY_PHOTO_RESPONSE = "key.photo.response"
private const val KEY_PREV_QUERY = "key.prev.query"

class PreferenceService(context: Context) {
    var service = context.getSharedPreferences(SHARED_PREF_STORE, Context.MODE_PRIVATE)
    var gson = Gson()

    fun getCachedResponse(): List<Photo> {
        return service.getString(KEY_PHOTO_RESPONSE, null)?.let {
            gson.fromJson(
                it,
                object : TypeToken<List<Photo>>() {}.type
            )
        } ?: emptyList()
    }

    fun updateCachedResponse(response: List<Photo>) {
        service.edit().putString(KEY_PHOTO_RESPONSE, gson.toJson(response)).apply()
    }

    companion object {
        private var INSTANCE: PreferenceService? = null
        fun getInstance(context: Context) = INSTANCE
            ?: PreferenceService(context).also {
                INSTANCE = it
            }
    }

    fun updatePrevQuery(tag: String) {
        service.edit().putString(KEY_PREV_QUERY, tag).apply()
    }

    fun getPrevQueryTag(): String {
        return service.getString(KEY_PREV_QUERY, null) ?: "meditation"
    }
}