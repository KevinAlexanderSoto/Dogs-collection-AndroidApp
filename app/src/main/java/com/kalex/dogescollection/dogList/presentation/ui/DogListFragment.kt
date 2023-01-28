package com.kalex.dogescollection.dogList.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kalex.dogescollection.databinding.DogListViewBinding
import com.kalex.dogescollection.dogList.model.data.dto.Dog

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DogListFragment : Fragment() {

    private var _binding: DogListViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = DogListViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val dogAdapter = DogAdapter()
            binding.doglistRecycler.layoutManager = GridLayoutManager(view.context,2)
            binding.doglistRecycler.adapter = dogAdapter
        dogAdapter.submitList(getFakeDogs())

            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)


    }
    private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1,
                1,
                "Chihuahua",
                "Toy",
                5.4,
                6.7,
                "",
                "12 - 15",
                "",
                10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Toy", 5.4, 6.7, "", "12 - 15", "", 10.5, 12.3
            )
        )
        return dogList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}