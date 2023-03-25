package com.kalex.dogescollection.doglist.di

import com.kalex.dogescollection.core.api.DogsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogListModule {

    @Provides
    @Singleton
    fun provideDogsRepository(api: DogsApi): com.kalex.dogescollection.doglist.model.repository.DogRepository {
        return com.kalex.dogescollection.doglist.model.repository.DogRepositoryImpl(api)
    }
}
