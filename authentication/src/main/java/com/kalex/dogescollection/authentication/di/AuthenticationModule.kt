package com.kalex.dogescollection.authentication.di

import com.kalex.dogescollection.authentication.model.AuthenticationRepository
import com.kalex.dogescollection.authentication.model.AuthenticationRepositoryImpl
import com.kalex.dogescollection.core.api.DogsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(api : DogsApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }
}