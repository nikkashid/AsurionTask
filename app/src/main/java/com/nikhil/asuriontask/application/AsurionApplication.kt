package com.nikhil.asuriontask.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AsurionApplication : Application() {

    lateinit var asurionApplication: Context

    init {
        instance = this
    }

    companion object {
        private var instance: AsurionApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        asurionApplication = applicationContext()
    }

//    @Provides
//    @Singleton
//    fun provideAssetManger(): AssetManager {
//        val assetsProvider: AssetManager = asurionApplication.assets
//        return assetsProvider
//    }

}