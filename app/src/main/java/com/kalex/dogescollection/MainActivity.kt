package com.kalex.dogescollection

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalex.dogescollection.camera.CameraFragment
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.databinding.ActivityMainBinding
import com.kalex.dogescollection.dogList.presentation.ui.DogListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DogListFragment.DogListFragmentActions {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var camerabutton: FloatingActionButton
    private var isUserLogged = true

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    @Inject
    lateinit var permissionHandler: PermissionHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        camerabutton = binding.cameraActionButton
        setNavBar()

        camerabutton.setOnClickListener {
            // Request camera permissions
            permissionHandler.start()
            lifecycleScope.launch {
                permissionHandler.currentAddState.collectLatest {
                    if (it) startCameraFragment()
                }
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    companion object {
        private const val CAMERA_FRAGMENT_TAG = "camera_fragment"
    }
}