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
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kalex.dogescollection.R
import com.kalex.dogescollection.authentication.FieldKey
import com.kalex.dogescollection.authentication.RegexPatterns
import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.authentication.epoxy.*
import com.kalex.dogescollection.common.PreferencesHandler
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

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

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
                textHint(getString(R.string.authentication_login_user_text_hint))
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
                textHint(getString(R.string.authentication_login_password_text_hint))
                regexValidation(Regex(RegexPatterns.PASSWORD_REGEX_PATTERN))
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
                textHint(getString(R.string.authentication_login_password_text_hint))
                regexValidation(Regex(RegexPatterns.PASSWORD_REGEX_PATTERN))
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
                id(7)
                buttonText(getString(R.string.authentication_login_button_text))
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
            onSuccess = {
                preferencesHandler.setLoggedInUser(it)
                findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToDogListFragment())
                        },
            onLoading = {},
            onError = {

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.error_title))
                    .setMessage(resources.getString(it))
                    .setPositiveButton(resources.getString(R.string.error_accept)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
            }
        )
    }
}