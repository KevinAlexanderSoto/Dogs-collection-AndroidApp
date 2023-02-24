package com.kalex.dogescollection.di

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.kalex.dogescollection.PermissionHandler
import com.kalex.dogescollection.api.ApiServiceInterceptor
import com.kalex.dogescollection.authentication.model.AuthenticationRepository
import com.kalex.dogescollection.authentication.model.AuthenticationRepositoryImpl
import com.kalex.dogescollection.common.Constants
import com.kalex.dogescollection.api.DogsApi
import com.kalex.dogescollection.common.Constants.LABEL_PATH
import com.kalex.dogescollection.common.Constants.MODEL_PATH
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.dogList.model.repository.DogRepository
import com.kalex.dogescollection.dogList.model.repository.DogRepositoryImpl
import com.kalex.dogescollection.tensorflow.Classifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.tensorflow.lite.support.common.FileUtil
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.nio.MappedByteBuffer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogModule {

    @Provides
    @Singleton
    fun provideDogApi(client: OkHttpClient): DogsApi = Retrofit.Builder()
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


    @Provides
    @Singleton
    fun provideDogsRepository(api : DogsApi): DogRepository {
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
    @Provides
    @ActivityScoped
    fun providePermissionHandler(@ActivityContext context: Context): PermissionHandler {
        return PermissionHandler(context as AppCompatActivity)
    }
    @Provides
    @ActivityScoped
    fun provideTfLiteModel(@ActivityContext context: Context): MappedByteBuffer {
        return FileUtil.loadMappedFile(context,MODEL_PATH)
    }
    @Provides
    @ActivityScoped
    fun provideClassifier(TfLiteModel: MappedByteBuffer,@ActivityContext context: Context): Classifier {
        return Classifier(TfLiteModel,FileUtil.loadLabels(context ,LABEL_PATH))
    }
}