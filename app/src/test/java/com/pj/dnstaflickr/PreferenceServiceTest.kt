package com.pj.dnstaflickr

import android.content.Context
import android.content.SharedPreferences
import com.pj.dnstaflickr.data.service.PreferenceService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PreferenceServiceTest {

    lateinit var preferenceService: PreferenceService

    @Before
    fun init() {
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context: Context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        preferenceService = PreferenceService.getInstance(context)
    }

    @Test
    @Throws(Exception::class)
    fun testGetValidToken() {
//        Mockito.`when`(preferenceService.updatePrevQuery("tag1"))
//        Assert.assertEquals("tag1", preferenceService.getPrevQueryTag())
    }
}