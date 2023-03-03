package com.kalex.dogescollection.di

import android.content.Context
import com.kalex.dogescollection.authentication.model.AuthenticationRepository
import com.kalex.dogescollection.authentication.model.AuthenticationRepositoryImpl
import com.kalex.dogescollection.core.api.DogsApi
import com.kalex.dogescollection.core.common.Constants
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
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
object ClassifierModule {

    @Provides
    @ActivityScoped
    fun provideTfLiteModel(@ActivityContext context: Context): MappedByteBuffer {
        return FileUtil.loadMappedFile(context, Constants.MODEL_PATH)
    }
    @Provides
    @ActivityScoped
    fun provideClassifier(TfLiteModel: MappedByteBuffer, @ActivityContext context: Context): Classifier {
        return Classifier(TfLiteModel, FileUtil.loadLabels(context , Constants.LABEL_PATH))
    }
}