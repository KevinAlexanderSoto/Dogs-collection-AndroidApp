package com.kalex.dogescollection.authentication.createaccount.presentation.iu

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyInputField
import com.kalex.dogescollection.authentication.epoxy.epoxyInputPassword
import com.kalex.dogescollection.databinding.FragmentCreateAccountBinding
import kotlinx.coroutines.flow.MutableStateFlow


class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var enableUser = false
        var enablePass = false
        val state = MutableStateFlow(false)
        fun updateInputFieldState(enable:Boolean){
            enableUser = enable
            state.value = enableUser && enablePass
        }
        fun updateInputPasswordState(enable:Boolean){
            enablePass = enable
            state.value = enableUser && enablePass
        }

        binding.CreateAccountEpoxyRecyclerView.withModels {
            epoxyInputField {
                id(1)
                //TODO: Add strings resources
                textHint("Usuario")
                regexValidation(Patterns.EMAIL_ADDRESS.toRegex())
                onValidationResult { valid ->
                    //TODO: implementEror message
                    updateInputFieldState(valid)
                }
                onIsFocus{
                    updateInputFieldState(false)
                }
            }
            epoxyInputPassword {
                id(2)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid ->
                    //TODO: implement Eror message
                    updateInputPasswordState(valid)
                }
                onIsFocus{
                    updateInputFieldState(false)
                }
            }
            epoxyInputPassword {
                id(2)
                //TODO: Add strings resources
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult { valid ->
                    //TODO: implement Eror message
                    updateInputPasswordState(valid)
                }
                onIsFocus{
                    updateInputFieldState(false)
                }
            }
            epoxyButton{
                id(3)
                buttonText("crear cuenta")
            }

        }
    }

}