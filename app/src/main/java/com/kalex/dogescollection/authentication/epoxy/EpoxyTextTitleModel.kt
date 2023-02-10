package com.kalex.dogescollection.authentication.epoxy

import android.content.Context
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_title_text)
abstract class EpoxyTextTitleModel: EpoxyModelWithHolder<EpoxyTextTitleModel.Holder>() {

    @EpoxyAttribute
    lateinit var titleText : String

    @EpoxyAttribute
     var textStyle : Int = R.style.title_text_32

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textView.text = titleText
        holder.textView.setTextAppearance(textStyle)

    }
    inner class Holder():  KotlinEpoxyHolder(){
        val textView by bind<AppCompatTextView>(R.id.textTitle)
    }
}