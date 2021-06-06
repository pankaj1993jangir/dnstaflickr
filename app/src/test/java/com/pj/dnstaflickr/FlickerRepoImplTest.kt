package com.pj.dnstaflickr

import android.content.Context
import com.pj.dnstaflickr.data.FlickerRepoImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FlickerRepoImplTest {

    lateinit var FlickerRepo: FlickerRepoImpl

    @Before
    fun init() {
        val context: Context = Mockito.mock(Context::class.java)
        FlickerRepo = FlickerRepoImpl.getInstance(context) as FlickerRepoImpl
    }

    @Test
    fun testGetValidToken() {
        val farm = 11
        val server = "22"
        val id = "33"
        val secret = "44"
        val url = "https://farm${farm}.staticflickr.com/${server}/${id}_${secret}_m.jpg"
        Assert.assertEquals(url, FlickerRepo.generateImageUrl(farm, server, id, secret))
    }
}