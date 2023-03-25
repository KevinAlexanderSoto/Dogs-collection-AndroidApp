package com.kalex.dogescollection

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalex.dogescollection.authentication.login.presentation.LoginFragmentDirections
import com.kalex.dogescollection.camera.camera.CameraFragmentDirections
import com.kalex.dogescollection.camera.camera.DogResultFragmentDirections
import com.kalex.dogescollection.core.common.AuthenticationSwitcherNavigator
import com.kalex.dogescollection.core.common.CameraSwitcherNavigator
import com.kalex.dogescollection.core.common.PermissionHandler
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AuthenticationSwitcherNavigator, CameraSwitcherNavigator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraButton: FloatingActionButton
    private var isUserLogged = true
    private lateinit var navController: NavController

    @Inject
    lateinit var preferencesHandler: com.kalex.dogescollection.core.common.PreferencesHandler

    @Inject
    lateinit var permissionHandler: PermissionHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        cameraButton = binding.cameraActionButton
        setNavBar()
        // Keep the splash screen visible for this Activity
        splashScreen.setKeepOnScreenCondition { false }
    }

    private fun startCameraFragment(buildNavController: NavController) {
        buildNavController.navigate(com.kalex.dogescollection.camera.R.id.camera_graph)
    }

    private fun setNavBar() {
        if (preferencesHandler.getLoggedInUser() == null) {
            isUserLogged = false
        }
        val buildNavController = setStartDestination(isUserLogged)
        navController = buildNavController
        NavigationUI.setupWithNavController(binding.bottomAppBar, buildNavController)

        appBarConfiguration = AppBarConfiguration(buildNavController.graph)

        setCameraButtonListener(buildNavController)
    }

    private fun setCameraButtonListener(buildNavController: NavController) {
        cameraButton.setOnClickListener {
            // Request camera permissions
            permissionHandler.start()
            lifecycleScope.launch {
                permissionHandler.currentPermissionState.collectLatest {
                    if (it) startCameraFragment(buildNavController)
                }
            }
        }
    }

    private fun setStartDestination(isUserLogged: Boolean): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater

        val graph: NavGraph = if (isUserLogged) {
            setBottomActionVisibility(View.VISIBLE)
            inflater.inflate(com.kalex.dogescollection.navigation.R.navigation.main_graph)
        } else {
            setBottomActionVisibility(View.GONE)
            inflater.inflate(com.kalex.dogescollection.authentication.R.navigation.authentication_graph)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
        return navController
    }
    private fun setBottomActionVisibility(state: Int) {
        binding.cameraActionButton.visibility = state
        binding.bottomAppBar.visibility = state
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }

    override fun onUserAuthenticated() {
        setBottomActionVisibility(View.VISIBLE)
        navController.navigate(com.kalex.dogescollection.navigation.R.id.MainNavigation)
    }

    override fun onCreateNewUser() {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
    }

    override fun onUserCreated() {
        setBottomActionVisibility(View.VISIBLE)
        navController.navigate(com.kalex.dogescollection.navigation.R.id.MainNavigation)
    }

    override fun onDogRecognised(foundDog: Dog) {
        val bundle = CameraFragmentDirections.actionCameraFragmentToDogResultFragment(foundDog)
        setBottomActionVisibility(View.GONE)
        navController.navigate(bundle)
    }

    override fun onDogAddToCollection() {
        setBottomActionVisibility(View.VISIBLE)
        navController.navigate(DogResultFragmentDirections.actionDogResultFragmentToDogListFragment())
    }

    override fun onRetryRecognizeDog() {
        navController.navigate(DogResultFragmentDirections.actionDogResultFragmentToCameraFragment())
        setBottomActionVisibility(View.VISIBLE)
    }
}
