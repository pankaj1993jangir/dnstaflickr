package com.pj.dnstaflickr.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pj.dnstaflickr.R
import com.pj.dnstaflickr.data.FlickerRepoImpl
import com.pj.dnstaflickr.domain.entity.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        FlickerRepoImpl().getPhotos("funny", 2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ photos ->
                showToast("Success")
            },
                { error ->
                    showToast("failed")
                }
            )
    }

}