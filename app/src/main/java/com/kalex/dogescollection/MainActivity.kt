package com.kalex.dogescollection

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.kalex.dogescollection.api.ApiServiceInterceptor
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.databinding.ActivityMainBinding
import com.kalex.dogescollection.dogList.presentation.ui.DogListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DogListFragment.DogListFragmentActions {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferencesHandler: PreferencesHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        var isUserLogged = true

        if (preferencesHandler.getLoggedInUser() == null) { isUserLogged = false}

       val buildNavController = setStartDestination(isUserLogged)

        //Set Up bottom bar
        NavigationUI.setupWithNavController(binding.bottomAppBar, buildNavController)

        appBarConfiguration = AppBarConfiguration(buildNavController.graph)
        setupActionBarWithNavController(buildNavController, appBarConfiguration)

    }
    private fun setStartDestination(isUserLogged : Boolean): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (isUserLogged){

            binding.cameraActionButton.visibility = View.VISIBLE
            binding.bottomAppBar.visibility = View.VISIBLE
            graph.setStartDestination(R.id.DogListFragment)

        }else {
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
}