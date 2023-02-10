package com.kalex.dogescollection.authentication.createaccount.presentation.iu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyInputField
import com.kalex.dogescollection.authentication.epoxy.epoxyInputPassword
import com.kalex.dogescollection.databinding.FragmentCreateAccountBinding


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
        binding.CreateAccountEpoxyRecyclerView.withModels {
            epoxyInputField{
                id(1)
                //TODO: Add strings resources
                textHint("Usuario")
                regexValidation(Regex(""))
                onValidationResult(){ valid ->
                    //TODO: implement
                    if (!valid){
                        Toast.makeText(context,"No empty input", Toast.LENGTH_LONG).show()
                    }
                }
            }
            epoxyInputPassword{
                id(2)
                textHint("Password")
                regexValidation(Regex(""))
                onValidationResult(){ valid ->
                    //TODO: implement
                    if (!valid){
                        Toast.makeText(context,"No empty input", Toast.LENGTH_LONG).show()
                    }
                }
            }
            epoxyInputPassword{
                id(2)
                textHint("confirm password")
                regexValidation(Regex(""))
                onValidationResult(){ valid ->
                    //TODO: implement
                    if (!valid){
                        Toast.makeText(context,"No empty input", Toast.LENGTH_LONG).show()
                    }
                }
            }
            epoxyButton{
                id(3)
                buttonText("crear cuenta")
            }

        }
    }

}