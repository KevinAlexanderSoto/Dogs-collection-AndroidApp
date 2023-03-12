package com.kalex.dogescollection.camera.di

import android.content.Context
import com.kalex.dogescollection.camera.tensorflow.Classifier
import com.kalex.dogescollection.core.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer

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
        return Classifier(
            TfLiteModel,
            FileUtil.loadLabels(context, Constants.LABEL_PATH)
        )
    }
}