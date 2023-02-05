package com.kalex.dogescollection.dogList.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.group
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.DogListDetailFragmentBinding
import com.kalex.dogescollection.dogList.model.data.dto.Dog
import com.kalex.dogescollection.dogList.presentation.epoxy.dogDetailInfo
import com.kalex.dogescollection.dogList.presentation.epoxy.dogDetailItem
import kotlinx.android.synthetic.main.dog_list_detail_fragment.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DogDetailFragment : Fragment() {

    private var _binding: DogListDetailFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DogListDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val args = bundle?.let { DogDetailFragmentArgs.fromBundle(it) }
with(binding){
    dog_name_text.text = args?.dog?.name_es
    dog_description_text.text = args?.dog?.temperament_en
    recyclerView.withModels {
        group {
            id("epoxyModelGroupDsl")
            layout(R.layout.vertical_linear_group)
            dogDetailInfo{
                id(1)
                detailsTitle("Famale")

                heightValue(args?.dog?.height_female)
                heightTitle("Height")
                weightValue(args?.dog?.weight_female)
                weightTitle("Weight")
            }
            dogDetailItem{
                id(2)
                detailsTitle(args?.dog?.dog_type)
            }
            dogDetailInfo{
                id(3)
                detailsTitle("Male")

                heightValue(args?.dog?.height_male)
                heightTitle("Height")
                weightValue(args?.dog?.weight_male)
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