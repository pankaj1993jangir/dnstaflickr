package com.pj.dnstaflickr.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import coil.load
import com.pj.dnstaflickr.R
import com.pj.dnstaflickr.databinding.ActivityMainBinding
import com.pj.dnstaflickr.databinding.FragmentPhotoDetailBinding

private const val URL = "key.url"
private const val TITLE = "key.title"

class PhotoDetailFragment : Fragment() {
    private var url: String? = null
    private var title: String? = null
    private lateinit var binding: FragmentPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
            title = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        binding.ivImage.load(url)
        binding.ivClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String, title: String) = PhotoDetailFragment().apply {
            arguments = Bundle().apply {
                putString(URL, url)
                putString(TITLE, title)
            }
        }
    }
}