package com.kalex.dogescollection.authentication.createaccount.presentation.iu

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.button.MaterialButton
import com.kalex.dogescollection.authentication.epoxy.*
import com.kalex.dogescollection.databinding.FragmentCreateAccountBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var enableUser = false
        var enablePass = false
        var enablePass2 = false
        val state = MutableStateFlow(false)
        fun updateInputFieldState(enable:Boolean){
            enableUser = enable
            state.value = enableUser && enablePass && enablePass2
        }
        fun updateInputPasswordState(enable:Boolean){
            enablePass = enable
            state.value = enableUser && enablePass && enablePass2
        }
        fun updateInputPassword2State(enable:Boolean){
            enablePass2 = enable
            state.value = enableUser && enablePass && enablePass2
        }

        binding.CreateAccountEpoxyRecyclerView.withModels {
            epoxyInputField {
                id(17)
                //TODO: Add strings resources
                textHint("Usuario")
                regexValidation(Patterns.EMAIL_ADDRESS.toRegex())
                onValidationResult { valid, currentText ->
                    //TODO: implementEror message
                    updateInputFieldState(valid)
                }
                onIsFocus{
                    updateInputFieldState(false)
                }
            }
            epoxyInputPassword {
                id(28)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid , currentText ->
                    //TODO: implement Eror message
                    updateInputPasswordState(valid)
                }
                onIsFocus{
                    updateInputPasswordState(false)
                }
            }
            epoxyInputPassword {
                id(39)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid, currentText ->
                    //TODO: implement Eror message
                    updateInputPassword2State(valid)
                }
                onIsFocus{
                    updateInputPassword2State(false)
                }
            }
            epoxyButton {
                id(7)//TODO: Add strings resources
                buttonText("Login")
                onClickListener {

                }
                enableButton { isEnable: MaterialButton ->
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            state.collectLatest {
                                isEnable.isEnabled = it
                            }
                        }
                    }
                }
            }
        }
    }
}