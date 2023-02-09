package com.kalex.dogescollection.authentication.login.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyInputField
import com.kalex.dogescollection.authentication.epoxy.epoxyInputPassword
import com.kalex.dogescollection.authentication.epoxy.epoxyTextButton
import com.kalex.dogescollection.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginEpoxyRecyclerView.withModels {
            epoxyInputField{
                id(1)
                texthint("Usuario")
                regexValidation(Regex(""))
                onValidationResult(){ valid ->
                    if (!valid){
                        Toast.makeText(context,"No empty input",LENGTH_LONG).show()
                    }
                }
            }
            epoxyInputPassword{
                id(2)
            }
            epoxyButton{
                id(3)
                buttonText("Login")
            }

            epoxyTextButton{
                id(4)
                buttonText("Register")
                topButtonText("Do not have an account?")
            }
        }
    }
}