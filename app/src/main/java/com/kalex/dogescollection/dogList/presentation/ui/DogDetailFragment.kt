package com.kalex.dogescollection.dogList.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.airbnb.epoxy.group
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.DogDetailFragmentBinding
import com.kalex.dogescollection.dogList.model.data.dto.Dog
import com.kalex.dogescollection.dogList.presentation.epoxy.dogDetailInfo
import com.kalex.dogescollection.dogList.presentation.epoxy.dogDetailItem

/**
 * A simple [Fragment] subclass for the Dog detail.
 */
class DogDetailFragment : Fragment() {

    private var _binding: DogDetailFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DogDetailFragmentBinding.inflate(inflater, container, false)
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
        with(binding) {
            dogimage.load(dog.image_url) {
                crossfade(true)
            }
            dognametext.text = dog.name_es
            dogdescriptiontext.text = dog.temperament_en
            //Epoxy SetUp
            recyclerView.withModels {
                group {
                    id("epoxyModelGroupDsl")
                    layout(R.layout.vertical_linear_group)
                    dogDetailInfo {
                        id(1)
                        detailsTitle("Famale")
                        //TODO : ADD STRINGS RESOURCES
                        heightValue(dog.height_female)
                        heightTitle("Height")
                        weightValue(dog.weight_female)
                        weightTitle("Weight")
                    }
                    dogDetailItem {
                        id(2)
                        detailsTitle(dog.dog_type)
                    }
                    dogDetailInfo {
                        id(3)
                        detailsTitle("Male")
                        //TODO : ADD STRINGS RESOURCES
                        heightValue(dog.height_male)
                        heightTitle("Height")
                        weightValue(dog.weight_male)
                        weightTitle("Weight")
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