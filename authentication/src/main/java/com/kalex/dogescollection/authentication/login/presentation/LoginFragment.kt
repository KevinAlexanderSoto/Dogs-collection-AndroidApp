package com.kalex.dogescollection.authentication.login.presentation

import android.content.Context
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kalex.dogescollection.authentication.R
import com.kalex.dogescollection.core.R as coreR
import com.kalex.dogescollection.authentication.FieldKey
import com.kalex.dogescollection.authentication.RegexPatterns
import com.kalex.dogescollection.authentication.RegexValidationState
import com.kalex.dogescollection.authentication.databinding.FragmentLoginBinding
import com.kalex.dogescollection.core.common.AuthenticationSwitcherNavigator
import com.kalex.dogescollection.core.common.EpoxyMarginCon
import com.kalex.dogescollection.core.common.PreferencesHandler
import com.kalex.dogescollection.core.common.networkstates.handleViewModelState
import com.kalex.dogescollection.core.epoxy.*
import com.kalex.dogescollection.core.model.dto.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel : LoginViewModel by viewModels()
    private lateinit var authSwitcherNavigator: AuthenticationSwitcherNavigator
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
                textMargin(com.kalex.dogescollection.core.common.EpoxyMarginCon(1f, 20f, 1f, 40f))
            }
            epoxyInputField {
                id(1)
                textHint(getString(R.string.authentication_login_user_text_hint))
                regexValidation(Patterns.EMAIL_ADDRESS.toRegex())
                onValidationResult { valid, currentText ->
                    //TODO: implementEror message
                    regexValidationState.updateInputFieldState(valid,currentText)
                }
            }
            epoxyInputPassword {
                id(2)
                textHint(getString(R.string.authentication_login_password_text_hint))
                regexValidation(Regex(RegexPatterns.PASSWORD_REGEX_PATTERN))
                regexError(resources.getString(R.string.authentication_password_regex_error))
                onValidationResult { valid, currentText ->
                    //TODO: implement Eror message
                    regexValidationState.updateInputPasswordState(valid,currentText)
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
                onClickListener { authSwitcherNavigator.onCreateNewUser() }
            }
        }
    }
    private fun handleOnCreateAccountStates() {
        handleViewModelState(loginViewModel.signInState,
            onSuccess = { handleSuccessStatus(it) },
            onLoading = { handleLoadingStatus(true) },
            onError = {
                handleLoadingStatus(false)
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(coreR.string.error_title))
                    .setMessage(resources.getString(it))
                    .setPositiveButton(resources.getString(coreR.string.error_accept)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        )
    }
    private fun handleSuccessStatus(user: User) {
        Toast.makeText(requireContext(),getString(R.string.authentication_login_success_message),Toast.LENGTH_SHORT).show()
        preferencesHandler.setLoggedInUser(user)
        authSwitcherNavigator.onUserAuthenticated()
    }
    private fun handleLoadingStatus(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgress.visibility = View.VISIBLE
            binding.loginEpoxyRecyclerView.visibility = View.GONE
        } else {
            binding.loginEpoxyRecyclerView.visibility = View.VISIBLE
            binding.linearProgress.visibility = View.GONE
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        authSwitcherNavigator = try {
            context as AuthenticationSwitcherNavigator
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }
}