package br.edu.ifsp.dmo.gtuner.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.gtuner.ui.util.PreferencesHelper

class TunerViewModelFactory(
    private val application: Application,
    private val preferencesHelper: PreferencesHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TunerViewModel::class.java)) {
            return TunerViewModel(application, preferencesHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}