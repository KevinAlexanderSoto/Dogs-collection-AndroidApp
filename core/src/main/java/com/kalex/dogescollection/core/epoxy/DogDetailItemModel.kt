package com.kalex.dogescollection.core.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class DogDetailItemModel : EpoxyModelWithHolder<DogDetailItemModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.dog_detail_item
    }

    @EpoxyAttribute
    lateinit var detailsTitle: String

    override fun bind(holder: Holder) {
        with(holder) {
            detailsTitleView.text = detailsTitle
        }
    }
    inner class Holder : KotlinEpoxyHolder() {
        val detailsTitleView by bind<TextView>(R.id.dog_item_title)
    }
}
