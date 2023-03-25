package com.kalex.dogescollection.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kalex.dogescollection.MainActivity
import com.kalex.dogescollection.R
import com.kalex.dogescollection.core.common.EpoxyMarginCon
import com.kalex.dogescollection.core.common.PreferencesHandler
import com.kalex.dogescollection.core.epoxy.epoxyButton
import com.kalex.dogescollection.core.epoxy.epoxyTextTitle
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
        binding.SettingEpoxyRecyclerView.withModels {
            epoxyTextTitle {
                id(0)
                titleText(resources.getString(R.string.settings_title))
                textMargin(EpoxyMarginCon(1f, 20f, 1f, 10f))
            }
            epoxyButton {
                id(1)
                buttonText(resources.getString(R.string.settings_log_out_button_text))
                initialButtonEnableState(true)
                onClickListener {
                    preferencesHandler.onLogOutUser()
                    val intent = Intent(this@SettingsFragment.requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                enableButton {}
            }
        }
    }
}
