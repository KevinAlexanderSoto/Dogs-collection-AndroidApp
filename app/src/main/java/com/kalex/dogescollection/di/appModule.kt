package com.kalex.dogescollection.di

import android.content.Context
import com.kalex.dogescollection.authentication.model.AuthenticationRepository
import com.kalex.dogescollection.authentication.model.AuthenticationRepositoryImpl
import com.kalex.dogescollection.core.api.DogsApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Provides
        @Singleton
        fun provideAuthenticationRepository(api : DogsApi): AuthenticationRepository {
            return AuthenticationRepositoryImpl(api)
        }
}

