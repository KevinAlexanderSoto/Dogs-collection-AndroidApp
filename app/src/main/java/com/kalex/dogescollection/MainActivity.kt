package com.kalex.dogescollection

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.kalex.dogescollection.common.AuthenticationSwitcherNavigator
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AuthenticationSwitcherNavigator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var camerabutton: FloatingActionButton
    private var isUserLogged = true
    private lateinit var navController : NavController
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
    }

    private fun startCameraFragment(buildNavController: NavController) {
        buildNavController.navigate(R.id.CameraFragmentNav)
    }

    private fun setNavBar() {

        if (preferencesHandler.getLoggedInUser() == null) {
            isUserLogged = false
        }
        val buildNavController = setStartDestination(isUserLogged)
        navController = buildNavController
        NavigationUI.setupWithNavController(binding.bottomAppBar, buildNavController)

        appBarConfiguration = AppBarConfiguration(buildNavController.graph)

        setCameraButtonListiner(buildNavController)

    }

    private fun setCameraButtonListiner(buildNavController: NavController) {
        camerabutton.setOnClickListener {
            // Request camera permissions
            permissionHandler.start()
            lifecycleScope.launch {
                permissionHandler.currentAddState.collectLatest {
                    if (it) startCameraFragment(buildNavController)
                }
            }
        }
    }

    private fun setStartDestination(isUserLogged: Boolean): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater

        val graph : NavGraph = if (isUserLogged) {
            setBottomActionVisibility(View.VISIBLE)
            inflater.inflate(R.navigation.main_graph)

        } else {
            setBottomActionVisibility(View.GONE)
            inflater.inflate(R.navigation.nav_graph)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
        return navController
    }
    private fun setBottomActionVisibility(state : Int ){
        binding.cameraActionButton.visibility = state
        binding.bottomAppBar.visibility = state
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onUserAuthenticated() {
        setBottomActionVisibility(View.VISIBLE)
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToDogListFragment())
    }

    override fun onCreateNewUser() {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
    }

    override fun onUserCreated() {
        setBottomActionVisibility(View.VISIBLE)
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToDogListFragment())
    }

}