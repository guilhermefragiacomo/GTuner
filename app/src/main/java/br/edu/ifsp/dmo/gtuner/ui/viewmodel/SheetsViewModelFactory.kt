package br.edu.ifsp.dmo.gtuner.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SheetsViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SheetsViewModel::class.java)) {
            return SheetsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}