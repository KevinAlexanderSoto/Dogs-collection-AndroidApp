package com.kalex.dogescollection.authentication.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_text_button)
abstract class EpoxyTextButtonModel: EpoxyModelWithHolder<EpoxyTextButtonModel.Holder>() {

    @EpoxyAttribute
    lateinit var buttonText : String

    @EpoxyAttribute
    lateinit var topButtonText : String

    @EpoxyAttribute
    lateinit var onClickListener : () -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.buttonTitleView.text = topButtonText
        holder.buttonView.text = buttonText
        holder.buttonView.setOnClickListener{
            onClickListener
        }

    }
    inner class Holder():  KotlinEpoxyHolder(){
        val buttonView by bind<MaterialButton>(R.id.authenticationTextButton)
        val buttonTitleView by bind<MaterialTextView>(R.id.ButtonTitle)
    }
}