package com.kalex.dogescollection.doglist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.doglist.presentation.composables.DogList
import com.kalex.dogescollection.doglist.presentation.viewmodel.DogCollectionViewModel
import com.kalex.dogescollection.doglist.presentation.viewmodel.DogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.kalex.dogescollection.core.R as coreR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DogListFragment : Fragment() {

    private val dogsViewModel: DogsViewModel by viewModels()
    private val collectionViewModel: DogCollectionViewModel by viewModels()

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Redirect to our own function, this close the app, but first ask the user for sure
            this@DogListFragment.onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                collectionViewModel.getDogCollection()
                HandleDogsByViewModel()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // Set this to true in order to trigger callbacks to Fragment#onOptionsItemSelected

        (requireActivity() as AppCompatActivity).apply {
            // Redirect system "Back" press to our dispatcher
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedDispatcher)
        }
    }

    @Composable
    private fun HandleDogsByViewModel() {
        val data = remember {
            mutableStateListOf<Dog>()
        }
        handleViewModelState(
            collectionViewModel.getCollectionState,
            onSuccess = {
                data.clear()
                data += it
            },
            onLoading = {
                // handleLoadingStatus(it)
            },
            onError = {
                handleErrorStatus(getString(it))
            },
        )
        if (data.isNotEmpty()) {
            handleSuccessStatus(data)
        }
    }

    private fun handleErrorStatus(exception: String) {
        // TODO: set strings and styles
        handleLoadingStatus(false)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(coreR.string.ErrorTitle))
            .setMessage(exception)
            .setPositiveButton(resources.getString(coreR.string.ErrorButtonText)) { _, _ ->
                dogsViewModel.getAllDogs()
            }
            .show()
    }

    @Composable
    private fun handleSuccessStatus(dogList: List<Dog>) {
        DogList(dogList, findNavController())
        handleLoadingStatus(false)
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
        } else {
        }
    }

    override fun onDestroyView() {
        // It is optional to remove since our dispatcher is lifecycle-aware. But it wouldn't hurt to just remove it to be on the safe side.
        backPressedDispatcher.remove()
        super.onDestroyView()
    }

    private fun onBackPressed() {
        // TODO: set strings and styles
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(coreR.string.app_log_out_title))
            .setMessage(resources.getString(coreR.string.app_log_out_message))
            .setPositiveButton(resources.getString(coreR.string.positive_button_text)) { _, _ ->
                activity?.finish()
            }
            .show()
    }
}
