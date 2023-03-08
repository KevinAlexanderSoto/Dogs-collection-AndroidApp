package com.kalex.dogescollection.camera.camera

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kalex.dogescollection.camera.R
import com.kalex.dogescollection.camera.tensorflow.ClassifierRepository
import com.kalex.dogescollection.core.common.CameraSwitcherNavigator
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment : BottomSheetDialogFragment(R.layout.fragment_camera) {

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private val cameraPreviewView: PreviewView
        get() = requireView().findViewById(R.id.viewFinder)

    private val circularindicator: CircularProgressIndicator
        get() = requireView().findViewById(R.id.circularindicator)

    private lateinit var takePhotoButton: FloatingActionButton
    private lateinit var cameraSwitcherNavigator: CameraSwitcherNavigator

    @Inject
    lateinit var classifierRepository: ClassifierRepository

    private val dogPredictViewModel: DogPredictViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takePhotoButton = view.findViewById<FloatingActionButton>(R.id.cameraButton)

        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraPreviewUseCase()

    }

    private fun takePictureUseCase() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        val contentValues = createContentValuesNameEntry()

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("kevs", "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    val imageBitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        output.savedUri
                    )
                    // BitmapFactory.decodeFile(output.savedUri.toString().replace("content://",""))
                }
            }
        )
    }

    private fun createContentValuesNameEntry(): ContentValues {
        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())

        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
    }

    private fun handlePredictedViewModel() {

        handleViewModelState(dogPredictViewModel.dogState,
            onSuccess = {
                handleSuccessStatus(it)
            },
            onLoading = {
                handleLoadingStatus(it)
            },
            onError = {
                handleErrorStatus(getString(it))
            }
        )
    }

    private fun handleErrorStatus(exception: String) {
        handleLoadingStatus(false)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.ErrorTitle))
            .setMessage(exception)
            .setPositiveButton(resources.getString(R.string.ErrorButtonText)) { dialog, _ ->
               dialog.dismiss()
            }
            .show()
    }

    private fun handleSuccessStatus(foundDog: Dog) {
        handleLoadingStatus(false)
        cameraSwitcherNavigator.onDogRecognised(foundDog)
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            circularindicator.visibility = View.VISIBLE
        } else {
            circularindicator.visibility = View.GONE
        }
    }

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    private fun imageAnalysisUseCase(): ImageAnalysis {
        //ImageAnalysis UseCase Section
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            lifecycleScope.launch {
                val confidence = classifierRepository.recognizeImage(imageProxy)

                if (confidence.confidence > 80.0) {
                    takePhotoButton.alpha = 1f
                    takePhotoButton.setOnClickListener {
                        dogPredictViewModel.getDogByPredictedId(confidence.DogId)
                        handlePredictedViewModel()
                    }
                } else {
                    takePhotoButton.setOnClickListener { null }
                    takePhotoButton.alpha = 0.3f
                }

                imageProxy.close()
            }
        }
        return imageAnalysis
    }

    private fun cameraPreviewUseCase() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraPreviewView.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = imageAnalysisUseCase()
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalysis
                )

            } catch (exc: Exception) {
                Log.e("kevs", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraSwitcherNavigator = try {
            context as CameraSwitcherNavigator
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement cameraSwitcherNavigator")
        }
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    }
}