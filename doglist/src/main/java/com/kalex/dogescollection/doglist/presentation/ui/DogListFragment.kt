package com.kalex.dogescollection.doglist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.doglist.databinding.DogListFragmentBinding
import com.kalex.dogescollection.doglist.presentation.viewmodel.DogCollectionViewModel
import com.kalex.dogescollection.doglist.presentation.viewmodel.DogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.kalex.dogescollection.core.R as coreR

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DogListFragment : Fragment() {

    private var _binding: DogListFragmentBinding? = null
    private val binding get() = _binding!!

    private val dogsViewModel: DogsViewModel by viewModels()
    private val collectionViewModel: DogCollectionViewModel by viewModels()

    @Inject
    lateinit var dogListAdapter: DogListAdapter

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
        _binding = DogListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // Set this to true in order to trigger callbacks to Fragment#onOptionsItemSelected

        (requireActivity() as AppCompatActivity).apply {
            // Redirect system "Back" press to our dispatcher
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedDispatcher)
        }
        setUpRecycler(dogListAdapter)
        binding.linearProgress.indeterminateAnimationType = LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_DISJOINT
        collectionViewModel.getDogCollection()

        handleDogsByViewModel(dogListAdapter)
    }

    private fun setUpRecycler(dogListAdapter: DogListAdapter) {
        binding.doglistRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.doglistRecycler.adapter = dogListAdapter
        dogListAdapter.onItemClick = { dog ->
            val bundle = DogListFragmentDirections.actionDogListFragmentToDogListDetailFragment(dog)
            findNavController().navigate(bundle)
        }
    }

    private fun handleDogsByViewModel(dogListAdapter: DogListAdapter) {
        handleViewModelState(
            collectionViewModel.getCollectionState,
            onSuccess = {
                handleSuccessStatus(it, dogListAdapter)
            },
            onLoading = {
                handleLoadingStatus(it)
            },
            onError = {
                handleErrorStatus(getString(it))
            },
        )
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

    private fun handleSuccessStatus(dogList: List<Dog>, dogListAdapter: DogListAdapter) {
        dogListAdapter.submitList(dogList)
        handleLoadingStatus(false)
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgress.visibility = View.VISIBLE
        } else {
            binding.linearProgress.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        // It is optional to remove since our dispatcher is lifecycle-aware. But it wouldn't hurt to just remove it to be on the safe side.
        backPressedDispatcher.remove()
        super.onDestroyView()
        _binding = null
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
