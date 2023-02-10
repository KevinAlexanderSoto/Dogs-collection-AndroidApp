package com.kalex.dogescollection.authentication.epoxy

import android.util.MutableBoolean
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@EpoxyModelClass(layout = R.layout.authentication_buttom)
abstract class EpoxyButtonModel : EpoxyModelWithHolder<EpoxyButtonModel.Holder>() {

    @EpoxyAttribute
    lateinit var buttonText : String

    @EpoxyAttribute
    var initialButtonEnableState : Boolean = false

    @EpoxyAttribute
    lateinit var enableButton : (button : MaterialButton) -> Unit

    @EpoxyAttribute
    lateinit var onClickListener : () -> Unit

    @EpoxyAttribute
    var textStyle : Int = R.style.title_text_18

    override fun bind(holder: Holder) {
        super.bind(holder)

        with(holder){
            buttonView.text = buttonText
            buttonView.setTextAppearance(textStyle)
            buttonView.isEnabled = initialButtonEnableState
            enableButton.invoke(holder.buttonView)
            buttonView.setOnClickListener{
                onClickListener.invoke()
            }
        }
    }
    inner class Holder():  KotlinEpoxyHolder(){
        val buttonView by bind<MaterialButton>(R.id.authenticationButton)
    }
}