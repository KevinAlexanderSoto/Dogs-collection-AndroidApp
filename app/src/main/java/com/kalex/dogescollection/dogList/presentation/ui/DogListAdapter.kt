package com.kalex.dogescollection.dogList.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.DogListItemBinding
import com.kalex.dogescollection.core.model.data.alldogs.Dog
import javax.inject.Inject


class DogListAdapter @Inject constructor() : ListAdapter<Dog, DogListAdapter.ViewHolder>(DiffUtilCallback) {

    var onItemClick: ((Dog) -> Unit)? = null
    var onLongItemClick: ((Dog) -> Unit)? = null

    inner class ViewHolder(private val binding: DogListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            with(binding) {
                dogName.text = dog.name_es
                if(dog.inCollection){
                    dogListCard.setCardBackgroundColor(ContextCompat.getColor(dogImage.context,R.color.white))
                    dogImage.setImageDrawable(null)
                    dogImage.load(dog.image_url) { crossfade(true) }
                    dogListCard.setOnClickListener{onItemClick?.invoke(dog)}
                }else{
                    dogImage.setImageDrawable(ContextCompat.getDrawable(dogImage.context,R.drawable.round_question_mark_24))
                }
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