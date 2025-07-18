package br.edu.ifsp.dmo.gtuner.ui.view.fragments

import android.R.attr.angle
import android.R.attr.pivotX
import android.R.attr.pivotY
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.gtuner.databinding.FragmentTunerBinding
import br.edu.ifsp.dmo.gtuner.ui.util.PreferencesHelper
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.TunerViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.TunerViewModelFactory


class TunerFragment : Fragment() {
    private var _binding: FragmentTunerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TunerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentTunerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = TunerViewModelFactory(requireActivity().application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(TunerViewModel::class.java)

        viewModel.onResume()

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        viewModel.onPause()
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {

    }

    fun setupObservers() {
        viewModel.hz.observe(viewLifecycleOwner) { hz ->
            hz?.let {
                binding.tvFrequency.text = it
            }
        }
        viewModel.note.observe(viewLifecycleOwner) { note ->
            note?.let {
                binding.tvNote.text = it
            }
        }
        viewModel.cents.observe(viewLifecycleOwner) { cents ->
            cents?.let {
                binding.tvCents.text = it.toString()
                binding.ivBar.rotation = cents;
                binding.ivBar.pivotY = binding.ivBar.height.toFloat()
            }
        }
    }
}