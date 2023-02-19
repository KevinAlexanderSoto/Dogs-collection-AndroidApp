package com.kalex.dogescollection

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PermissionHandler @Inject constructor(
    private val context: AppCompatActivity
) {
    private val _dogCollectionState = MutableStateFlow<Boolean>(false)
    val currentAddState: StateFlow<Boolean>
        get() = _dogCollectionState

    fun start() {
        if (allPermissionsGranted()) {
            _dogCollectionState.value = true
        } else {
            aksToPermission(REQUIRED_PERMISSIONS)

        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun aksToPermission(REQUIRED_PERMISSIONS: Array<String>) {
        requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private val requestPermissionLauncher = context.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted: Map<String, Boolean> ->
        val permissionToAsk = mutableListOf<String>()

        if (isGranted.values.contains(false)) {
            REQUIRED_PERMISSIONS.forEach {
                when {
                    shouldShowRequestPermissionRationale(context, it) -> {
                        //se pueden pedir otra vez
                        permissionToAsk.add(it)
                    }
                }
            }
            if (permissionToAsk.size > 0) {
                executeDialogForNegativePermission(true) {
                    aksToPermission(permissionToAsk.toTypedArray())
                }
            } else {
                executeDialogForNegativePermission(false) {

                }
            }

        } else {
            //acepto los permisos
            _dogCollectionState.value = true
        }
    }


    private fun executeDialogForNegativePermission(isRationale: Boolean, callback: () -> Unit) {
        //TODO: Add strings resources and style
        MaterialAlertDialogBuilder(context)
            .setTitle("ACEPTE LOS PERMISOS")
            .setMessage(
                "Si no los aceptas, no vas a poder tener mas perritos en la coleccion." +
                        "Intenta ejecutar la camara otra vez, y dale al boton de ACEPTAR!!!!"
            )
            .setPositiveButton("Vale, voy a reflexionar ") { dialog, _ ->
                callback.invoke()
                if (!isRationale) {
                    //Take the User to the app settings
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(context, intent, null)
                }
                dialog.dismiss()
            }
            .show()
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                mutableListOf(
                    android.Manifest.permission.CAMERA,
                ).toTypedArray()
            } else {
                mutableListOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,

                    ).toTypedArray()
            }
    }
}
