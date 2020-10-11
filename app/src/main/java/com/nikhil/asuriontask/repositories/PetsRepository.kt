package com.nikhil.asuriontask.repositories


import android.annotation.SuppressLint
import com.nikhil.asuriontask.application.AsurionApplication
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@SuppressLint("CheckResult")
class PetsRepository @Inject constructor() {

    suspend fun readJsonFile(fileName: String): String? {

        var json: String? = null
        json = try {
            lateinit var inputStream: InputStream

            when (fileName) {
                "config" -> inputStream =
                    AsurionApplication.applicationContext().assets.open("config.json")
                "pets" -> inputStream =
                    AsurionApplication.applicationContext().assets.open("pets.json")
            }
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return json
        }

        return json

    }

}