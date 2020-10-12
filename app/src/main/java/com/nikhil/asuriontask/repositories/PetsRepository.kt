package com.nikhil.asuriontask.repositories

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@SuppressLint("CheckResult")
class PetsRepository @Inject constructor() {

    suspend fun suspendFunction(fileName: String) = withContext(Dispatchers.IO) {

        var apiResponse: String = ""

        when (fileName) {
            "config" -> apiResponse =
                URL("https://nikkashid.github.io/AsurionTask/app/src/main/assets/pets.json").readText()
            "pets" -> apiResponse =
                URL("https://nikkashid.github.io/AsurionTask/app/src/main/assets/pets.json").readText()
        }

        return@withContext apiResponse
    }


}