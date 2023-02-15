package com.kalex.dogescollection.authentication

import com.kalex.dogescollection.authentication.FieldKey.DATA_FIELD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegexValidationState @Inject constructor() {
    private var isDataFieldValidate: Boolean = false
    private var isPasswordField1Validate: Boolean = false
    private var isPasswordField2Validate: Boolean = false

    private val fieldValues = mutableMapOf<FieldKey,String>()

    private val _regexState = MutableStateFlow(true)
    val regexState: StateFlow<Boolean>
        get() = _regexState

    fun updateInputFieldState(enable: Boolean,fieldValue: String = "") {
        isDataFieldValidate = enable
        fieldValues[DATA_FIELD] = fieldValue
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }

    fun updateInputPasswordState(enable: Boolean,fieldValue: String = "") {
        isPasswordField1Validate = enable
        fieldValues[FieldKey.PASSWORD_ONE] = fieldValue
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }

    fun updateInputPassword2State(enable: Boolean,fieldValue: String = "") {
        isPasswordField2Validate = enable
        fieldValues[FieldKey.PASSWORD_TWO] = fieldValue
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }
    fun getFieldValue(key: FieldKey) = fieldValues[key] ?: ""
}

enum class FieldKey {
     DATA_FIELD ,
     PASSWORD_ONE ,
      PASSWORD_TWO ,
}