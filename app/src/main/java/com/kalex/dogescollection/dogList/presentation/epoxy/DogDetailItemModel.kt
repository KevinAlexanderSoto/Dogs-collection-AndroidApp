package com.kalex.dogescollection.dogList.presentation.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.dogList.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.dog_detail_item)
abstract class DogDetailItemModel : EpoxyModelWithHolder<DogDetailItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var detailsTitle : String

    override fun bind(holder: Holder) {
        with(holder){
            detailsTitleview.text =detailsTitle
        }
    }
    inner class Holder : KotlinEpoxyHolder() {
        val detailsTitleview by bind<TextView>(R.id.dog_item_title)

    }
}