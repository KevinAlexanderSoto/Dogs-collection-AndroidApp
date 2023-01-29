package com.kalex.dogescollection.dogList.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kalex.dogescollection.databinding.DogListItemBinding
import com.kalex.dogescollection.dogList.model.data.dto.Dog
import javax.inject.Inject


class DogListAdapter @Inject constructor() : ListAdapter<Dog, DogListAdapter.ViewHolder>(DiffUtilCallback) {

    class ViewHolder(val view: DogListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(dog: Dog) {
            with(view) {
                itemtitle.text = dog.name_es
            }
        }
    }
    private object DiffUtilCallback : DiffUtil.ItemCallback<Dog>(){
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean =
             oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}