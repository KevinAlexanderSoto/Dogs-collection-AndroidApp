package com.kalex.dogescollection.dogList.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.group
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.DogListDetailFragmentBinding
import com.kalex.dogescollection.dogList.presentation.epoxy.dogDetailInfo

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
        binding.recyclerView.withModels {
            group {
                id("epoxyModelGroupDsl")
                layout(R.layout.vertical_linear_group)
                dogDetailInfo{
                    id(1)
                    detailsTitle("HOLA MUNDO")

                    heightValue("content")
                    heightTitle("content")
                    weightValue("content")
                    weightTitle("content")
                }
                dogDetailInfo{
                    id(1)
                    detailsTitle("HOLA MUNDO")

                    heightValue("content")
                    heightTitle("content")
                    weightValue("content")
                    weightTitle("content")
                }
                dogDetailInfo{
                    id(1)
                    detailsTitle("HOLA MUNDO")

                    heightValue("content")
                    heightTitle("content")
                    weightValue("content")
                    weightTitle("content")
                }
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}