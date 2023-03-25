package com.kalex.dogescollection.core.epoxy

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.EpoxyMarginCon
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class EpoxyTextTitleModel : EpoxyModelWithHolder<EpoxyTextTitleModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.authentication_title_text
    }

    @EpoxyAttribute
    lateinit var titleText: String

    @EpoxyAttribute
    var textStyle: Int = R.style.title_text_32

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

                EpoxyMarginCon.dpToPx(textMargin.top, context),

                EpoxyMarginCon.dpToPx(textMargin.right, context),

                EpoxyMarginCon.dpToPx(textMargin.bottom, context),
            )
    }
    inner class Holder() : KotlinEpoxyHolder() {
        val textView by bind<AppCompatTextView>(R.id.textTitle)
        val parentLayout by bind<ConstraintLayout>(R.id.parentlayout)
    }
}
