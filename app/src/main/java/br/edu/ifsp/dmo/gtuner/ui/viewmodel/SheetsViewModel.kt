package br.edu.ifsp.dmo.gtuner.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.data.repository.SheetRepository
import br.edu.ifsp.dmo.gtuner.ui.util.CameraHelper
import kotlinx.coroutines.launch

class SheetsViewModel(application : Application) : AndroidViewModel(application) {
    private val sheet_repository = SheetRepository(application)

    private val _sheets = MutableLiveData<List<Sheet>>()
    val sheets : LiveData<List<Sheet>> = _sheets

    private val _saved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean> = _saved

    private val _bitmap = MutableLiveData<Bitmap>()
    val bitmap : LiveData<Bitmap> = _bitmap

    private val REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1002

    private lateinit var cameraHelper: CameraHelper
    private val REQUEST_CAMERA_CODE = 1001

    init {
        checkDatabase()
        _saved.value = false
    }

    fun checkDatabase(){
        viewModelScope.launch {
            val sheet_list = (sheet_repository.get_all())
            _sheets.postValue(sheet_list)
        }
    }

    fun addSheet(name : String, author : String, arrangment: String, uri: Uri?) {
        viewModelScope.launch {
            if (uri != null) {
                val sheet = Sheet.create_new_sheet(
                    name = name,
                    author = author,
                    arrangment = arrangment,
                    url = uri.toString()
                )

                if (sheet_repository.add(sheet) > 0) {
                    _saved.value = true/*
                    Toast.makeText(
                        getApplication<Application>(), getApplication<Application>().getString(
                            R.string.loaded_sheet
                        ), Toast.LENGTH_SHORT
                    ).show()*/
                } else {/*
                    Toast.makeText(
                        getApplication<Application>(),
                        getApplication<Application>().getString(R.string.error_uploading_sheet),
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            }
            else {/*
                Toast.makeText(
                    getApplication<Application>(),
                    getApplication<Application>().getString(R.string.error_uploading_sheet),
                    Toast.LENGTH_SHORT
                ).show()*/
            }
        }
    }
}