package com.kalex.dogescollection

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalex.dogescollection.camera.CameraFragment
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.databinding.ActivityMainBinding
import com.kalex.dogescollection.dogList.presentation.ui.DogListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DogListFragment.DogListFragmentActions {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var camerabutton: FloatingActionButton
    private var isUserLogged = true

    @Inject
    lateinit var preferencesHandler: PreferencesHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        camerabutton = binding.cameraActionButton
        setNavBar()

        camerabutton.setOnClickListener {
            // Request camera permissions
            if (allPermissionsGranted()) {
                startCameraFragment()
            } else {
                aksToPermission(REQUIRED_PERMISSIONS)
            }
        }
    }

    private fun startCameraFragment() {
        CameraFragment().show(supportFragmentManager, CAMERA_FRAGMENT_TAG)
    }

    private fun setNavBar() {
        setSupportActionBar(binding.toolbar)
        if (preferencesHandler.getLoggedInUser() == null) {
            isUserLogged = false
        }
        val buildNavController = setStartDestination(isUserLogged)
        NavigationUI.setupWithNavController(binding.bottomAppBar, buildNavController)

        appBarConfiguration = AppBarConfiguration(buildNavController.graph)
        setupActionBarWithNavController(buildNavController, appBarConfiguration)
    }

    private fun setStartDestination(isUserLogged: Boolean): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (isUserLogged) {

            binding.cameraActionButton.visibility = View.VISIBLE
            binding.bottomAppBar.visibility = View.VISIBLE
            graph.setStartDestination(R.id.DogListFragment)

        } else {
            binding.cameraActionButton.visibility = View.GONE
            binding.bottomAppBar.visibility = View.GONE
            graph.setStartDestination(R.id.LoginFragment)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
        return navController
    }

    override fun showMenuItem() {
        binding.toolbar.visibility = View.VISIBLE
    }

    override fun hideMenuItem() {
        binding.cameraActionButton.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.toolbar.visibility = View.GONE
    }
    private fun aksToPermission(REQUIRED_PERMISSIONS: Array<String>) {
                    requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted: Map<String, Boolean> ->
            val permissionToAsk = mutableListOf<String>()

            if (isGranted.values.contains(false)) {
                REQUIRED_PERMISSIONS.forEach {
                    when {
                        shouldShowRequestPermissionRationale(it) -> {
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
                startCameraFragment()
            }
        }
    private fun executeDialogForNegativePermission(isRationale :Boolean,callback : ()->Unit){
        //TODO: Add strings resources and style
        MaterialAlertDialogBuilder(this)
            .setTitle("ACEPTE LOS PERMISOS")
            .setMessage("Si no los aceptas, no vas a poder tener mas perritos en la coleccion." +
                    "Intenta ejecutar la camara otra vez, y dale al boton de ACEPTAR!!!!"
                    )
            .setPositiveButton("Vale, voy a reflexionar ") { dialog, _ ->
                callback.invoke()
                if(!isRationale){
                    //Take the User to the app settings
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    companion object {
        private const val CAMERA_FRAGMENT_TAG = "camera_fragment"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA,
               android.Manifest.permission.WRITE_EXTERNAL_STORAGE,

            ).toTypedArray()
    }
}