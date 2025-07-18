package br.edu.ifsp.dmo.gtuner.ui.view.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.databinding.FragmentSheetBinding
import br.edu.ifsp.dmo.gtuner.ui.activities.MainActivity
import br.edu.ifsp.dmo.gtuner.ui.adapter.SheetAdapter
import br.edu.ifsp.dmo.gtuner.ui.listener.SheetItemListener
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SheetsViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.SheetsViewModelFactory


class SheetFragment(private val activity: MainActivity) : Fragment(), SheetItemListener {
    private var _binding: FragmentSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SheetsViewModel
    private lateinit var sheetAdapter: SheetAdapter

    private val REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1002
    private val PICK_PDF_REQUEST_CODE = 954
    private var sheet_uri: Uri? = null
    private val PICK_PDF_FILE = 2

    private var photo_taken = false

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

        binding.btnBackFromImage.setOnClickListener {
            binding.imageLayout.visibility = View.GONE
            binding.sheetListLayout.alpha = 1.0F
        }

        binding.btnUploadSheet.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, PICK_PDF_REQUEST_CODE)
        }

        binding.btnTakePhoto.setOnClickListener {
            photo_taken = true
            activity.takePhoto()
        }

        binding.btnCreateSheet.setOnClickListener {

            val name = binding.etSheetName.text.toString()
            val author = binding.etSheetAuthor.text.toString()
            val arrangment = binding.etSheetArrangment.text.toString()

            if (photo_taken) {
                activity.savePhotoSheet(name, author, arrangment)
                photo_taken = false
            }
            viewModel.addSheet(name, author, arrangment, sheet_uri)

            binding.sheetListLayout.visibility = View.VISIBLE
            binding.sheetAddLayout.visibility = View.GONE
        }
    }

    fun setupObservers() {
        viewModel.sheets.observe(viewLifecycleOwner) { sheets ->
            sheets?.let {
                System.out.println(it)
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
        if (sheet.url != null) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(sheet.url.toUri(), "application/pdf")
            startActivity(intent)
        } else {
            if (sheet.bitmap != null) {
                binding.ivSheetBitmap.setImageBitmap(sheet.bitmap)
                binding.imageLayout.visibility = View.VISIBLE
                binding.sheetListLayout.alpha = 0.5F
            }
        }
    }
}