package com.nikhil.asuriontask.dependencyInjection

import com.nikhil.asuriontask.repositories.PetsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PetsRepositoryModule {

    @Provides
    @Singleton
    fun providePetsRepository(/*assetManager: AssetManager*/): PetsRepository {
        return PetsRepository(/*assetManager*/)
    }
}