package com.kalex.dogescollection.authentication.login.presentation.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.DiffResult
import com.google.android.material.button.MaterialButton
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyInputField
import com.kalex.dogescollection.authentication.epoxy.epoxyInputPassword
import com.kalex.dogescollection.authentication.epoxy.epoxyTextButton
import com.kalex.dogescollection.authentication.epoxy.epoxyTextTitle
import com.kalex.dogescollection.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
         var enableuser = false
         var enablepass = false
         val state = MutableStateFlow(false)
        binding.loginEpoxyRecyclerView.withModels {
            epoxyTextTitle{
                id(0)
                titleText("Log in")
            }
            epoxyInputField{
                id(1)
                //TODO: Add strings resources
                textHint("Usuario")
                regexValidation( Patterns.EMAIL_ADDRESS.toRegex())
                onValidationResult{ valid ->
                    //TODO: implement
                    if (!valid){
                        enableuser = false
                        state.value = enableuser && enablepass
                        //TODO: Change this
                        Toast.makeText(context,"No empty input",LENGTH_LONG).show()
                    }else{

                        enableuser = true
                        state.value = enableuser && enablepass
                    }
                }
            }
            epoxyInputPassword{
                id(2)
                textHint("Password")
                regexValidation(Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{5,}$"))
                onValidationResult{ valid ->
                    //TODO: implement
                    if (!valid){
                        enablepass = false
                        state.value = enableuser && enablepass
                        //TODO: Change this
                        Toast.makeText(context,"No empty input",LENGTH_LONG).show()
                    }else{
                        enablepass = true
                        state.value = enableuser && enablepass
                    }
                }
            }
            epoxyButton{
                id(3)
                buttonText("Login")
                onClickListener{

                }
                enableButton{ isEnable: MaterialButton ->
                    lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                            state.collectLatest {
                                isEnable.isEnabled = it
                            }
                        }

                    }
                }
            }

            epoxyTextButton{
                id(4)
                buttonText("Register")
                topButtonText("Do not have an account?")
                onClickListener {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment())
                }
            }
        }
    }
}