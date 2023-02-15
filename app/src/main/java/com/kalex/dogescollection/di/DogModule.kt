package com.kalex.dogescollection.di

import android.app.Activity
import android.content.Context
import com.kalex.dogescollection.authentication.model.AuthenticationRepository
import com.kalex.dogescollection.authentication.model.AuthenticationRepositoryImpl
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.api.DogsApi
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import com.kalex.dogescollection.dogList.model.repository.DogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
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
    fun ProvideDogsRepository(api : DogsApi): DogRepository {
        return DogRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideAuthenticationRepository(api : DogsApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }


}

@Module
@InstallIn(ActivityComponent::class)
object SharedPreferencesModule {

    @Provides
    @ActivityScoped
    fun providePreferencesHandler(@ActivityContext context: Context): PreferencesHandler {
        return PreferencesHandler(context as Activity)
    }

}