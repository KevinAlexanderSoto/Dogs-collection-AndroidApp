package com.kalex.dogescollection.authentication.login.presentation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
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
import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.authentication.createaccount.presentation.CreateAccountFragmentDirections
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyInputField
import com.kalex.dogescollection.authentication.epoxy.epoxyInputPassword
import com.kalex.dogescollection.authentication.epoxy.epoxyTextButton
import com.kalex.dogescollection.authentication.epoxy.epoxyTextTitle
import com.kalex.dogescollection.authentication.login.presentation.LoginFragmentDirections
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.common.networkstates.handleViewModelState
import com.kalex.dogescollection.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel : LoginViewModel by viewModels()
    @Inject
    lateinit var regexValidationState: RegexValidationState

    @Inject
    lateinit var preferencesHandler: PreferencesHandler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        regexValidationState.updateInputFieldState(false)
        regexValidationState.updateInputPasswordState(false)
        regexValidationState.updateInputPassword2State(true)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginEpoxyRecyclerView.withModels {
            epoxyTextTitle {
                id(0)
                titleText(getString(R.string.authentication_login_title))
            }
            epoxyInputField {
                id(1)
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
                id(2)
                textHint(getString(R.string.authentication_login_password_text_hint))
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid, currentText ->
                    //TODO: implement Eror message
                    regexValidationState.updateInputPasswordState(valid,currentText)
                }
                onIsFocus {
                    regexValidationState.updateInputPasswordState(false)
                }
            }
            epoxyButton {
                id(3)
                buttonText(getString(R.string.authentication_login_button_text))
                onClickListener {
                    loginViewModel.signIn(
                        regexValidationState.getFieldValue(FieldKey.DATA_FIELD),
                        regexValidationState.getFieldValue(FieldKey.PASSWORD_ONE)
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

            epoxyTextButton {
                id(4)
                buttonText(getString(R.string.authentication_login_buttonText_text))
                topButtonText(getString(R.string.authentication_login_buttonText_title))
                onClickListener {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
                }
            }
        }

    }
    private fun handleOnCreateAccountStates() {
        handleViewModelState(loginViewModel.signInState,
            onSuccess = {
                Toast.makeText(requireContext(),getString(R.string.authentication_login_success_message),Toast.LENGTH_LONG).show()
                preferencesHandler.setLoggedInUser(it)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDogListFragment())
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