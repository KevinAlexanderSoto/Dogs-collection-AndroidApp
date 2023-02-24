package com.kalex.dogescollection.epoxy

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.EpoxyMarginCon
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_title_text)
abstract class EpoxyTextTitleModel: EpoxyModelWithHolder<EpoxyTextTitleModel.Holder>() {

    @EpoxyAttribute
    lateinit var titleText : String

    @EpoxyAttribute
     var textStyle : Int = R.style.title_text_32

    @EpoxyAttribute
    var textMargin: EpoxyMarginCon = EpoxyMarginCon(0f, 0f, 0f, 0f)

    override fun bind(holder: Holder) {
        super.bind(holder)
        val context = holder.parentLayout.context
        holder.textView.text = titleText
        holder.textView.setTextAppearance(textStyle)
        (holder.parentLayout.layoutParams as ViewGroup.MarginLayoutParams)
            .setMargins(
                EpoxyMarginCon.dpToPx(textMargin.left, context),
                EpoxyMarginCon.dpToPx(textMargin.top,context),
                EpoxyMarginCon.dpToPx(textMargin.right,context),
                EpoxyMarginCon.dpToPx(textMargin.bottom,context)
            )
    }
    inner class Holder():  KotlinEpoxyHolder(){
        val textView by bind<AppCompatTextView>(R.id.textTitle)
        val parentLayout by bind<ConstraintLayout>(R.id.parentlayout)
    }
}