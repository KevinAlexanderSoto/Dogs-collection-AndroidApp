package com.kalex.dogescollection.core.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.button.MaterialButton
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class EpoxyButtonModel : EpoxyModelWithHolder<EpoxyButtonModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.authentication_buttom
    }

    @EpoxyAttribute
    lateinit var buttonText: String

    @EpoxyAttribute
    var initialButtonEnableState: Boolean = false

    @EpoxyAttribute
    lateinit var enableButton: (button: MaterialButton) -> Unit

    @EpoxyAttribute
    lateinit var onClickListener: () -> Unit

    @EpoxyAttribute
    var textStyle: Int = R.style.title_text_18

    override fun bind(holder: Holder) {
        super.bind(holder)

        with(holder) {
            buttonView.text = buttonText
            buttonView.setTextAppearance(textStyle)
            buttonView.isEnabled = initialButtonEnableState
            enableButton.invoke(holder.buttonView)
            buttonView.setOnClickListener {
                onClickListener.invoke()
            }
        }
    }
    inner class Holder() : KotlinEpoxyHolder() {
        val buttonView by bind<MaterialButton>(R.id.authenticationButton)
    }
}
