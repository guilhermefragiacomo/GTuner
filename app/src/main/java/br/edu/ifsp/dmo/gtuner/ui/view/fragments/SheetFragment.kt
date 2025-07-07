package br.edu.ifsp.dmo.gtuner.ui.view.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.ui.adapter.SheetAdapter
import br.edu.ifsp.dmo.gtuner.ui.listener.SheetItemListener
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SheetsViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SheetsViewModelFactory
import br.edu.ifsp.dmo.gtuner.databinding.FragmentSheetBinding

class SheetFragment : Fragment(), SheetItemListener {
    private var _binding: FragmentSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SheetsViewModel
    private lateinit var sheetAdapter: SheetAdapter

    private val PICK_PDF_REQUEST_CODE = 954
    private var sheet_uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sheetAdapter = SheetAdapter(this)
        binding.recyclerViewSheets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sheetAdapter
        }

        val factory = SheetsViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(SheetsViewModel::class.java)

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.ibAddSheet.setOnClickListener {
            binding.sheetListLayout.visibility = View.GONE
            binding.sheetAddLayout.visibility = View.VISIBLE
        }

        binding.btnBackFromAdd.setOnClickListener {
            binding.sheetListLayout.visibility = View.VISIBLE
            binding.sheetAddLayout.visibility = View.GONE
        }

        binding.btnUploadSheet.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, PICK_PDF_REQUEST_CODE)
        }

        binding.btnCreateSheet.setOnClickListener {

            val name = binding.etSheetName.text.toString()
            val author = binding.etSheetAuthor.text.toString()
            val arrangment = binding.etSheetArrangment.text.toString()

            viewModel.addSheet(name, author, arrangment, sheet_uri)
        }
    }

    fun setupObservers() {
        viewModel.sheets.observe(viewLifecycleOwner) { sheets ->
            sheets?.let {
                sheetAdapter.submitDataset(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            sheet_uri = data?.data
        }
    }

    override fun onSheetClick(sheet: Sheet) {
        TODO("Not yet implemented")
    }
}