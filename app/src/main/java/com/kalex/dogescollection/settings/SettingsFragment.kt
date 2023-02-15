package com.kalex.dogescollection.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import com.kalex.dogescollection.R
import com.kalex.dogescollection.authentication.epoxy.epoxyButton
import com.kalex.dogescollection.authentication.epoxy.epoxyTextTitle
import com.kalex.dogescollection.common.PreferencesHandler
import com.kalex.dogescollection.databinding.FragmentLoginBinding
import com.kalex.dogescollection.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SettingEpoxyRecyclerView.withModels {
            epoxyTextTitle{
                id(0)
                titleText("Configuraciones")
            }
            epoxyButton{
                id(1)
                buttonText("Cerrar seccion")
                initialButtonEnableState(true)
                onClickListener{
                    preferencesHandler.onLogOutUser()
                    getActivity()?.finish()
                }
                enableButton{

                }
            }
        }
    }

}