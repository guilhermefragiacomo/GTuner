package br.edu.ifsp.dmo.gtuner.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.data.repository.SheetRepository
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : AndroidViewModel(application) {
    private val sheet_repository = SheetRepository(application)

    private val _saved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean> = _saved

    init {
        _saved.value = false
    }

    fun addSheet(name : String, author : String, arrangment: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val sheet = Sheet.create_new_sheet(
                name = name,
                author = author,
                arrangment = arrangment,
                url = "0",
                bitmap = bitmap
            )

            if (sheet_repository.add(sheet) > 0) {
                _saved.value = true
            }
        }
    }
}