package com.kalex.dogescollection.dogList.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.DogListFragmentBinding
import com.kalex.dogescollection.dogList.presentation.viewmodel.DogsViewModel
import com.kalex.dogescollection.dogList.presentation.viewmodel.LatestNewsUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DogListFragment : Fragment() {

    private var _binding: DogListFragmentBinding? = null
    private val dogsViewModel: DogsViewModel by viewModels()

    @Inject
    lateinit var dogListAdapter :DogListAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = DogListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler(dogListAdapter)

        dogsViewModel.getAllDogs()

        getDogsViewModel(dogListAdapter)

        //

    }

    private fun getDogsViewModel(dogListAdapter: DogListAdapter) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dogsViewModel.dogState.collectLatest {
                    when (it) {
                        is LatestNewsUiState.Error -> TODO()
                        is LatestNewsUiState.Loading -> Log.d("KEVS", "LOADING")
                        is LatestNewsUiState.Success -> dogListAdapter.submitList(it.dog.dogs)
                    }
                }
            }
        }
    }

    private fun setUpRecycler(dogListAdapter: DogListAdapter) {
        binding.doglistRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.doglistRecycler.adapter = dogListAdapter
        dogListAdapter.onItemClick = {dog ->
            val bundle = DogListFragmentDirections.actionDogListFragmentToDogListDetailFragment(dog)
            findNavController().navigate(bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}