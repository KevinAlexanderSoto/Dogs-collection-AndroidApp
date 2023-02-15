package com.kalex.dogescollection.dogList.presentation.epoxy


import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

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
            detailsTitleView.text =detailsTitle
            heightValueView.text = heightValue
            heightTitleView.text = heightTitle
            weightValueView.text = weightValue
            weightTitleView.text = weightTitle
        }
    }
    inner class Holder : KotlinEpoxyHolder() {
        val detailsTitleView by bind<TextView>(R.id.dog_description_title)
        val heightValueView by bind<TextView>(R.id.dog_height_value)
        val heightTitleView by bind<TextView>(R.id.dog_height_title)

        val weightValueView by bind<TextView>(R.id.dog_weight_value)
        val weightTitleView by bind<TextView>(R.id.dog_weight_title)
    }
}

