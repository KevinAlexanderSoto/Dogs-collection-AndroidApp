package com.kalex.dogescollection.camera

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.CameraSwitcherNavigator
import com.kalex.dogescollection.common.networkstates.handleViewModelState
import com.kalex.dogescollection.databinding.FragmentDogResultBinding
import com.kalex.dogescollection.dogList.model.data.alldogs.Dog
import com.kalex.dogescollection.dogList.presentation.ui.DogDetailFragmentArgs
import com.kalex.dogescollection.dogList.presentation.viewmodel.DogCollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [DogResultFragment.newInstance] factory method to
 * create an instance of this fragment.
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
            //TODO: Add strings resources
            confirmtext.text = "Este es tu Perror?"

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
            handleErrorStatus("No se anadio ")
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
            .setPositiveButton(resources.getString(R.string.ErrorbuttonText)) { dialog, which ->
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