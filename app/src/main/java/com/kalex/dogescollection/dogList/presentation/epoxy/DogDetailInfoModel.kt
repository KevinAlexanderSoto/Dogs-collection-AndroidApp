package com.kalex.dogescollection.dogList.presentation.epoxy

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.databinding.ActivityMainBinding.bind
import com.kalex.dogescollection.dogList.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.dog_detail_info_item)
abstract class DogDetailInfoModel : EpoxyModelWithHolder<DogDetailInfoModel.Holder>() {

     @EpoxyAttribute
    lateinit var detailsTitle : String
     @EpoxyAttribute
    lateinit var heightValue : String
     @EpoxyAttribute
    lateinit var heightTitle : String
     @EpoxyAttribute
    lateinit var weightValue : String
     @EpoxyAttribute
    lateinit var weightTitle : String


    override fun bind(holder: Holder) {
        with(holder){
            detailsTitleview.text =detailsTitle
            heightValueview.text = heightValue
            heightTitleview.text = heightTitle
            weightValueview.text = weightValue
            weightTitleview.text = weightTitle
        }
    }
    inner class Holder : KotlinEpoxyHolder() {
        val detailsTitleview by bind<TextView>(R.id.dog_description_title)
        val heightValueview by bind<TextView>(R.id.dog_height_value)
        val heightTitleview by bind<TextView>(R.id.dog_height_title)

        val weightValueview by bind<TextView>(R.id.dog_weight_value)
        val weightTitleview by bind<TextView>(R.id.dog_weight_title)
    }
}

