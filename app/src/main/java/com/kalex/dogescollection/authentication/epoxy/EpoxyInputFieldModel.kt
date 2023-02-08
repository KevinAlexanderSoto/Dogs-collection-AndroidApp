package com.kalex.dogescollection.authentication.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_input_field)
abstract class EpoxyInputFieldModel  : EpoxyModelWithHolder<EpoxyInputFieldModel.Holder>() {

    @EpoxyAttribute
    lateinit var texthint : String

    @EpoxyAttribute
    lateinit var validation : () -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textfieldlayoutview.hint = texthint
        holder.textfieldeditview.setOnFocusChangeListener{ view,focus ->

        }
    }
    inner class Holder():  KotlinEpoxyHolder(){
        val textfieldlayoutview by bind<TextInputLayout>(R.id.textFieldlayout)
        val textfieldeditview by bind<TextInputEditText>(R.id.textFieldEdit)
    }
}