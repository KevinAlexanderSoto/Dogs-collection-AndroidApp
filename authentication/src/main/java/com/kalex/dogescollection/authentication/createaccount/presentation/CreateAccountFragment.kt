package com.kalex.dogescollection.authentication.createaccount.presentation

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kalex.dogescollection.authentication.R
import com.kalex.dogescollection.authentication.FieldKey
import com.kalex.dogescollection.authentication.RegexPatterns
import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.authentication.databinding.FragmentCreateAccountBinding
import com.kalex.dogescollection.core.common.AuthenticationSwitcherNavigator
import com.kalex.dogescollection.core.common.PreferencesHandler
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.epoxy.ComparableKey
import com.kalex.dogescollection.core.epoxy.epoxyButton
import com.kalex.dogescollection.core.epoxy.epoxyInputField
import com.kalex.dogescollection.core.epoxy.epoxyInputPassword
import com.kalex.dogescollection.core.model.dto.User

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var authSwitcherNavigator: AuthenticationSwitcherNavigator

    @Inject
    lateinit var regexValidationState: RegexValidationState

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    private val createAccountViewModel: CreateAccountViewModel by viewModels()
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
                    regexValidationState.updateInputFieldState(valid, currentText)
                }
            }
            epoxyInputPassword {
                id(28)
                textHint(getString(R.string.authentication_login_password_text_hint))
                regexError(resources.getString(R.string.authentication_password_regex_error))
                regexValidation(Regex(RegexPatterns.PASSWORD_REGEX_PATTERN))
                onValidationResult { valid, currentText ->
                    regexValidationState.updateInputPasswordState(valid, currentText)
                }
            }
            epoxyInputPassword {
                id(39)
                textHint(getString(R.string.authentication_login_password_confirm_text_hint))
                regexValidation(Regex(RegexPatterns.PASSWORD_REGEX_PATTERN))
                onValidationResult { valid, currentText ->
                    regexValidationState.updateInputPassword2State(valid, currentText)
                }
                regexError(resources.getString(R.string.authentication_password_regex_error))
                isComparable(true)
                comparablePassword {
                    mapOf(
                        ComparableKey.COMPARABLE_PASSWORD_TEXT to regexValidationState.getFieldValue(
                            FieldKey.PASSWORD_ONE
                        ),
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
                        regexValidationState.regexState.collectLatest {
                            isEnable.isEnabled = it
                        }
                    }
                }
            }
        }
        setUpNavBar()
    }

    private fun setUpNavBar() {
        binding.toolbar.title = resources.getString(R.string.authentication_create_user_screen_title)
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun handleOnCreateAccountStates() {
        handleViewModelState(createAccountViewModel.authenticationState,
            onSuccess = { handleSuccessStatus(it) },
            onLoading = { handleLoadingStatus(true) },
            onError = {
                handleLoadingStatus(false)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.error_title))
                    .setMessage(resources.getString(it))
                    .setPositiveButton(resources.getString(R.string.error_accept)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        )
    }

    private fun handleSuccessStatus(user: User) {
        Toast.makeText(
            requireContext(),
            getString(R.string.authentication_login_success_message),
            Toast.LENGTH_SHORT
        ).show()
        preferencesHandler.setLoggedInUser(user)
        authSwitcherNavigator.onUserCreated()
    }

    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgress.visibility = View.VISIBLE
            binding.CreateAccountEpoxyRecyclerView.visibility = View.GONE
        } else {
            binding.CreateAccountEpoxyRecyclerView.visibility = View.VISIBLE
            binding.linearProgress.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authSwitcherNavigator = try {
            context as AuthenticationSwitcherNavigator
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement authSwitcherNavigator")
        }
    }
}