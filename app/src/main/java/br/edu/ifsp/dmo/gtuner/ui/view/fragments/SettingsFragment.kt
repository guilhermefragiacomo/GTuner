package br.edu.ifsp.dmo.gtuner.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.gtuner.databinding.FragmentSettingsBinding
import br.edu.ifsp.dmo.gtuner.ui.util.PreferencesHelper
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SettingsViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SettingsViewModelFactory

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SettingsViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)

        preferencesHelper = PreferencesHelper(requireContext())

        binding.etFrequency.setText(preferencesHelper.getA4Frequency().toString())
        binding.etFrequencySensibility.setText(preferencesHelper.getFrequencySensibility().toString())
        binding.switchNoteDisplay.isChecked = preferencesHelper.getNoteDisplay()

        System.out.println(binding.etFrequency.text.toString())

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.btnSaveSettings.setOnClickListener {
            var frequency = binding.etFrequency.text.toString()
            var frequency_sensibility = binding.etFrequencySensibility.text.toString()
            var note_display = binding.switchNoteDisplay.isChecked

            preferencesHelper.saveA4Frequency(frequency.toInt())
            preferencesHelper.saveFrequencySensibility(frequency_sensibility.toFloat())
            preferencesHelper.saveNoteDisplay(note_display)
        }
    }

    fun setupObservers() {

    }
}