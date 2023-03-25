package com.kalex.dogescollection.doglist.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.airbnb.epoxy.group
import com.kalex.dogescollection.core.epoxy.dogDetailInfo
import com.kalex.dogescollection.core.epoxy.dogDetailItem
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import com.kalex.dogescollection.doglist.R
import com.kalex.dogescollection.doglist.databinding.DogDetailFragmentBinding

/**
 * A simple [Fragment] subclass for the Dog detail.
 */
class DogDetailFragment : Fragment() {

    private var _binding: DogDetailFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DogDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { DogDetailFragmentArgs.fromBundle(it) }

        if (args != null) {
            setUpViews(args.dog!!)
            setUpNavBar()
        }
    }

    private fun setUpNavBar() {
        binding.toolbar.title = resources.getString(R.string.dog_detail_title)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpViews(dog: Dog) {
        with(binding) {
            dogimage.load(dog.image_url) {
                crossfade(true)
            }
            dognametext.text = dog.name_es
            dogdescriptiontext.text = dog.temperament
            // Epoxy SetUp
            recyclerView.withModels {
                group {
                    id("epoxyModelGroupDsl")
                    layout(R.layout.vertical_linear_group)
                    dogDetailInfo {
                        id(1)
                        detailsTitle(resources.getString(R.string.dog_detail_famale_title))
                        heightValue(dog.height_female)
                        heightTitle(resources.getString(R.string.dog_detail_height_title))
                        weightValue(dog.weight_female)
                        weightTitle(getString(R.string.dog_detail_weight_title))
                    }
                    dogDetailItem {
                        id(2)
                        detailsTitle(dog.dog_type)
                    }
                    dogDetailInfo {
                        id(3)
                        detailsTitle(getString(R.string.dog_detail_male_title))
                        heightValue(dog.height_male)
                        heightTitle(resources.getString(R.string.dog_detail_height_title))
                        weightValue(dog.weight_male)
                        weightTitle(getString(R.string.dog_detail_weight_title))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
