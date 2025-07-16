package br.edu.ifsp.dmo.gtuner.ui.util

import android.content.Context

class PreferencesHelper(context : Context) {
    companion object{
        const val FILE_NAME = "GTunerPrefs"
        const val A4_FREQUENCY = "A4Frequency"
        const val FREQUENCY_SENSIBILITY = "FrequencySensibility"
        const val NOTE_DISPLAY = "NoteDisplay"
    }

    private val sharedPreferences = context.applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveA4Frequency(a4frequency: Int) {
        sharedPreferences.edit().putInt(A4_FREQUENCY, a4frequency).apply()
    }

    fun getA4Frequency(): Int {
        return sharedPreferences.getInt(A4_FREQUENCY, 440)
    }

    fun saveFrequencySensibility(frequencySensibility: Float) {
        sharedPreferences.edit().putFloat(FREQUENCY_SENSIBILITY, frequencySensibility).apply()
    }

    fun getFrequencySensibility(): Float {
        return sharedPreferences.getFloat(FREQUENCY_SENSIBILITY, 2.0F)
    }

    fun saveNoteDisplay(noteDisplay: Boolean) {
        sharedPreferences.edit().putBoolean(NOTE_DISPLAY, noteDisplay).apply()
    }

    fun getNoteDisplay(): Boolean {
        return sharedPreferences.getBoolean(NOTE_DISPLAY, false)
    }
}