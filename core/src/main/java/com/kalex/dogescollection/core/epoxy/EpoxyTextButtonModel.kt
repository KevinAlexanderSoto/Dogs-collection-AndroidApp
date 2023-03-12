package com.kalex.dogescollection.core.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class EpoxyTextButtonModel: EpoxyModelWithHolder<EpoxyTextButtonModel.Holder>() {

    override fun getDefaultLayout(): Int {
        return  R.layout.authentication_text_button
    }

    @EpoxyAttribute
    lateinit var buttonText : String

    @EpoxyAttribute
    lateinit var topButtonText : String

    @EpoxyAttribute
    var textStyle : Int = R.style.title_text_18

    @EpoxyAttribute
    lateinit var onClickListener : () -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.buttonTitleView.text = topButtonText
        holder.buttonView.text = buttonText
        holder.buttonView.setTextAppearance(textStyle)
        holder.buttonView.setOnClickListener{
            onClickListener.invoke()
        }

    }
    inner class Holder(): KotlinEpoxyHolder(){
        val buttonView by bind<MaterialButton>(R.id.authenticationTextButton)
        val buttonTitleView by bind<MaterialTextView>(R.id.ButtonTitle)
    }
}