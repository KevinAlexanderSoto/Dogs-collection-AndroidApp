package com.kalex.dogescollection.camera.camera.camera

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kalex.dogescollection.camera.R
import com.kalex.dogescollection.core.common.CameraSwitcherNavigator
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import dagger.hilt.android.AndroidEntryPoint

/**
 *This fragment show the dog recognized and ask the user if it is correct
 *when the user click the positive button, the dog is add to the collection
 *else the fragment camera is going to open again.
 *@author Kevin Alexander Soto (Kalex)
 */
@AndroidEntryPoint
class DogResultFragment : Fragment() {

    private var _binding: FragmentDogResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraSwitcherNavigator: CameraSwitcherNavigator

    private val collectionViewModel: DogCollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { DogDetailFragmentArgs.fromBundle(it) }
        if (args != null) {
            setUpViews(args.dog!!)
        }

    }

    private fun setUpViews(dog: Dog) {
        with(binding){
            dognametext.text = dog.name_es
            dogimage.load(dog.image_url) {
                crossfade(true)
            }
            confirmtext.text = resources.getText(R.string.result_screen_confirm_text)
            binding.linearProgress.indeterminateAnimationType = LinearProgressIndicator.INDETERMINATE_ANIMATION_TYPE_DISJOINT
            positiveButton.setOnClickListener{addDogToUserCollection(dog)}
            negativeButton.setOnClickListener{goBackToCameraFragment()}
        }
    }

    private fun goBackToCameraFragment(){
        cameraSwitcherNavigator.onRetryRecognizeDog()
    }

    private fun addDogToUserCollection(dog: Dog) {
        collectionViewModel.addDogToCollection(dog.id.toLong())
        handleAddDogByViewModel()
    }
    private fun handleAddDogByViewModel() {

        handleViewModelState(collectionViewModel.currentAddState,
            onSuccess = {
                handleAddSuccessStatus(it)
            },
            onLoading = {
                handleLoadingStatus(it)
            },
            onError = {
                handleErrorStatus(getString(it))
            }
        )
    }

    private fun handleAddSuccessStatus(isAdded: Boolean) {
        handleLoadingStatus(false)
        if (isAdded) {
            cameraSwitcherNavigator.onDogAddToCollection()
        } else {
            handleErrorStatus(resources.getString(R.string.result_screen_add_error))
        }
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgress.visibility = View.VISIBLE
        } else {
            binding.linearProgress.visibility = View.GONE
        }
    }

    private fun handleErrorStatus(exception: String) {
        //TODO: set strings and styles
        handleLoadingStatus(false)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.ErrorTitle))
            .setMessage(exception)
            .setPositiveButton(resources.getString(R.string.ErrorButtonText)) { dialog, which ->
               //TODO:

            }
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraSwitcherNavigator = try {
            context as CameraSwitcherNavigator
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement cameraSwitcherNavigator")
        }
    }

}