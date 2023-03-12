package com.kalex.dogescollection.camera.tensorflow

import androidx.camera.core.ImageProxy
import com.kalex.dogescollection.camera.camera.utils.BitmapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



class ClassifierRepository @Inject constructor() {
    @Inject
    lateinit var  classifier: Classifier

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognitionConfidence =
        withContext(Dispatchers.IO) {
            DogRecognitionConfidence("", 0F)
            val bitMapImage = BitmapUtils.getBitmap(imageProxy)
            if (bitMapImage == null) {
                return@withContext DogRecognitionConfidence("", 0F)
            } else {
                return@withContext classifier.recognizeImage(bitMapImage).first()
            }
        }

}