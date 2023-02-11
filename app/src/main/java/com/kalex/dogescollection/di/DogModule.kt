package com.kalex.dogescollection.di

import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.dogList.model.data.DogsApi
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import com.kalex.dogescollection.dogList.model.repository.DogRepositoryImpl
import dagger.Component
import dagger.Component.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogModule {

    @Provides
    @Singleton
    fun provideDogApi(): DogsApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DogsApi::class.java)

    @Provides
    @Singleton
    fun userRepositoryProvide(api : DogsApi): DogRepository {
        return DogRepositoryImpl(api)
    }


}