package com.nikhil.asuriontask.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nikhil.asuriontask.repositories.PetsRepository

class HomeViewModel @ViewModelInject constructor(private val petsRepository: PetsRepository) :
    ViewModel() {

    suspend fun getConfigInfo(fileName: String): String? {
        return petsRepository.readJsonFile(fileName)
    }
}