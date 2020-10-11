package com.nikhil.asuriontask.ui.fragments

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nikhil.asuriontask.R
import com.nikhil.asuriontask.databinding.FragmentDetailsBinding
import com.nikhil.asuriontask.entities.PetsEntity

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var detailsFragment: FragmentDetailsBinding

    lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsFragment = FragmentDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)

        val detailsFragmentArgs = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        val petPOJO = detailsFragmentArgs!!.pet

        settingWebView(petPOJO)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun settingWebView(petPOJO: PetsEntity?) {

        detailsFragment.wvPetsDetails.webViewClient = WebViewClient()

        detailsFragment.wvPetsDetails.apply {
            loadUrl(petPOJO!!.content_url.toString())
            settings.safeBrowsingEnabled = true
        }

        detailsFragment.wvPetsDetails.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                detailsFragment.pbLoading.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                detailsFragment.pbLoading.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }

}