package com.kalex.dogescollection.core.epoxy


import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class DogDetailInfoModel : EpoxyModelWithHolder<DogDetailInfoModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return  R.layout.dog_detail_info_item
    }
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

