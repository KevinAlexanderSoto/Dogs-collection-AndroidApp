package com.kalex.dogescollection.authentication.epoxy

import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.red
import androidx.core.graphics.toColor
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
    lateinit var regexValidation : Regex

    @EpoxyAttribute
    lateinit var onValidationResult : (result:Boolean) -> Unit

    @JvmField
    @EpoxyAttribute
    var isOptional: Boolean = false

    private var currentText : String = ""
    private lateinit var textFieldLayoutView : TextInputLayout
    override fun bind(holder: Holder) {
        super.bind(holder)
        textFieldLayoutView = holder.textFieldLayoutView
        holder.textFieldLayoutView.hint = texthint
        holder.textFieldEditView.setOnFocusChangeListener{ _ ,focus :Boolean ->
            currentText = holder.textFieldEditView.text.toString()
            if(!focus) {
                handleOptional{
                    handleNullOrEmpty(currentText){
                        handleRegex()
                    }
                }
            }else{
                textFieldLayoutView.error = null
            }

        }
    }

    private fun onValidationError(textFieldLayoutView: TextInputLayout) {
        textFieldLayoutView.error = "error"
    }

    private fun handleNullOrEmpty(currentText: String, nextStep: () -> Unit){
        if(currentText.isNotEmpty()){
            nextStep()
        }else{
            handleError()
        }
    }
    private fun handleError(){
        onValidationError(textFieldLayoutView)
        onValidationResult.invoke(false)
    }
    private fun handleRegex(){
        if ( regexValidation.matches(currentText)){
            onValidationResult.invoke(true)
        }else{
            handleError()
        }
    }
    private fun handleOptional( nextStep: () -> Unit){
        if(!isOptional ){
            nextStep()
        }else{
            handleRegex()
        }
    }
    inner class Holder():  KotlinEpoxyHolder(){
        val textFieldLayoutView by bind<TextInputLayout>(R.id.textFieldlayout)
        val textFieldEditView by bind<TextInputEditText>(R.id.textFieldEdit)
    }
}