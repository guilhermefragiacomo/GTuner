package br.edu.ifsp.dmo.gtuner.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.gtuner.databinding.FragmentSettingsBinding
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SettingsViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SettingsViewModelFactory

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel

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

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {

    }

    fun setupObservers() {

    }
}