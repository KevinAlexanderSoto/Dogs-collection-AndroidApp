package com.kalex.dogescollection.authentication

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegexValidationState @Inject constructor(
    initialDataFieldValidate: Boolean = false,
    initialPasswordField1Validate: Boolean = false,
    initialPasswordField2Validate: Boolean = false
) {
    private var isDataFieldValidate: Boolean
    private var isPasswordField1Validate: Boolean
    private var isPasswordField2Validate: Boolean

    init {
        isDataFieldValidate = initialDataFieldValidate
        isPasswordField1Validate = initialPasswordField1Validate
        isPasswordField2Validate = initialPasswordField2Validate
    }

    private val _regexState = MutableStateFlow(true)
    val regexState: StateFlow<Boolean>
        get() = _regexState

    fun updateInputFieldState(enable: Boolean) {
        isDataFieldValidate = enable
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }

    fun updateInputPasswordState(enable: Boolean) {
        isPasswordField1Validate = enable
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }

    fun updateInputPassword2State(enable: Boolean) {
        isPasswordField2Validate = enable
        _regexState.value =
            isDataFieldValidate && isPasswordField1Validate && isPasswordField2Validate
    }
}