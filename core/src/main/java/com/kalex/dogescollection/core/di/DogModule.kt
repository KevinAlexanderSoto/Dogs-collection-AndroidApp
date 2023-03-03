package com.kalex.dogescollection.core.di

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.kalex.dogescollection.core.common.PermissionHandler
import com.kalex.dogescollection.core.api.ApiServiceInterceptor

import com.kalex.dogescollection.core.api.DogsApi
import com.kalex.dogescollection.core.common.Constants
import com.kalex.dogescollection.core.common.PreferencesHandler

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogModule {

    @Provides
    @Singleton
    fun provideDogApi(client: OkHttpClient):DogsApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DogsApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(ApiServiceInterceptor)
            .build()

}

@Module
@InstallIn(ActivityComponent::class)
object SharedPreferencesModule {

    @Provides
    @ActivityScoped
    fun providePreferencesHandler(@ActivityContext context: Context): PreferencesHandler {
        return PreferencesHandler(context as Activity)
    }
    @Provides
    @ActivityScoped
    fun providePermissionHandler(@ActivityContext context: Context): PermissionHandler {
        return PermissionHandler(context as AppCompatActivity)
    }
}