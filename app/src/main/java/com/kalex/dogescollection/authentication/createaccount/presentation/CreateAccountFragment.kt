package com.kalex.dogescollection.authentication.createaccount.presentation

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.button.MaterialButton
import com.kalex.dogescollection.R
import com.kalex.dogescollection.authentication.FieldKey
import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.authentication.epoxy.*
import com.kalex.dogescollection.common.networkstates.handleViewModelState
import com.kalex.dogescollection.databinding.FragmentCreateAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var regexValidationState: RegexValidationState

    private val createAccountViewModel : CreateAccountViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        regexValidationState.updateInputFieldState(false)
        regexValidationState.updateInputPasswordState(false)
        regexValidationState.updateInputPassword2State(false)
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.CreateAccountEpoxyRecyclerView.withModels {
            epoxyInputField {
                id(17)
                //TODO: Add strings resources
                textHint("Usuario")
                regexValidation(Patterns.EMAIL_ADDRESS.toRegex())
                onValidationResult { valid, currentText ->
                    //TODO: implementEror message
                    regexValidationState.updateInputFieldState(valid,currentText)
                }
                onIsFocus {
                    regexValidationState.updateInputFieldState(false)
                }
            }
            epoxyInputPassword {
                id(28)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid, currentText ->
                    //TODO: implement Eror message
                    regexValidationState.updateInputPasswordState(valid,currentText)
                }
                onIsFocus {
                    regexValidationState.updateInputPasswordState(false)
                }
            }
            epoxyInputPassword {
                id(39)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid, currentText ->
                    //TODO: implement Eror message
                    regexValidationState.updateInputPassword2State(valid,currentText)
                }
                onIsFocus {
                    regexValidationState.updateInputPassword2State(false)
                }
                isComparable(true)
                comparablePassword{
                    mapOf(
                        ComparableKey.COMPARABLE_PASSWORD_TEXT to regexValidationState.getFieldValue(FieldKey.PASSWORD_ONE)
                    ,
                    ComparableKey.COMPARABLE_PASSWORD_ERROR to getString(R.string.not_equals_passwords_error)
                    )

                }
            }
            epoxyButton {
                id(7)//TODO: Add strings resources
                buttonText("Login")
                onClickListener {
                    createAccountViewModel.createAccount(
                        regexValidationState.getFieldValue(FieldKey.DATA_FIELD),
                        regexValidationState.getFieldValue(FieldKey.PASSWORD_ONE),
                        regexValidationState.getFieldValue(FieldKey.PASSWORD_TWO)
                    )
                    handleOnCreateAccountStates()
                }
                enableButton { isEnable: MaterialButton ->
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            regexValidationState.regexState.collectLatest {
                                isEnable.isEnabled = it
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleOnCreateAccountStates() {
        handleViewModelState(createAccountViewModel.authenticationState,
            onSuccess = {Toast.makeText(context,"login correcto",Toast.LENGTH_LONG).show()},
            onLoading = {},
            onError = {
it
                Toast.makeText(context,"ERROR DEACONOCIDO",Toast.LENGTH_LONG).show()
            }
        )
    }
}