package br.edu.ifsp.dmo.gtuner.ui.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.dmo.gtuner.audio.calculators.AudioCalculator
import br.edu.ifsp.dmo.gtuner.audio.core.Callback
import br.edu.ifsp.dmo.gtuner.audio.core.Recorder
import br.edu.ifsp.dmo.gtuner.ui.util.PreferencesHelper
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.roundToInt


class TunerViewModel(application : Application, preferencesHelper: PreferencesHelper) : AndroidViewModel(application)  {
    private var recorder: Recorder? = null
    private var audioCalculator: AudioCalculator? = null
    private var handler: Handler? = null

    private val noteNames: Array<String> = arrayOf(
        "C", "C#", "D", "D#", "E", "F",
        "F#", "G", "G#", "A", "A#", "B"
    )

    private val noteNames2: Array<String> = arrayOf(
        "Dó", "Dó#", "Ré", "Ré#", "Mi", "Fá",
        "Fá#", "Sól", "Sól#", "Lá", "Lá#", "Si"
    )

    private val _amp = MutableLiveData<String>()
    val amp : LiveData<String> = _amp

    private val _db = MutableLiveData<String>()
    val db : LiveData<String> = _db

    private val _hz = MutableLiveData<String>()
    val hz : LiveData<String> = _hz

    private val _note = MutableLiveData<String>()
    val note : LiveData<String> = _note

    private val _cents = MutableLiveData<Float>()
    val cents : LiveData<Float> = _cents

    private val callback: Callback = object : Callback {
        override fun onBufferAvailable(buffer: ByteArray?) {
            audioCalculator!!.setBytes(buffer)
            val amplitude = audioCalculator!!.amplitude
            val decibel = audioCalculator!!.decibel
            val frequency = audioCalculator!!.frequency

            var amp = ("$amplitude Amp").toString()
            var db = ("$decibel db").toString()
            var hz = ("$frequency Hz").toString()

            handler!!.post {
                _amp.value = amp
                _db.value = db
                _hz.value = hz

                var a4frequency = preferencesHelper.getA4Frequency()
                var actualNoteNames = if (preferencesHelper.getNoteDisplay()) noteNames2 else noteNames

                val noteNumber = (12 * log2(frequency / a4frequency) + 69).roundToInt()
                val noteIndex = noteNumber % 12
                val octave = (noteNumber / 12) - 1

                val closestFreq = a4frequency * 2.0.pow((noteNumber - 69) / 12.0)
                val centsOff = 1200 * log2(frequency / closestFreq)
                if (noteIndex > 0 && noteIndex < actualNoteNames.size) {
                    _note.value = "${actualNoteNames[noteIndex]}$octave"
                    _cents.value = centsOff.toFloat()
                }
            }
        }
    }

    init {
        _amp.value = ""
        _db.value = ""
        _hz.value = ""
        recorder = Recorder(callback)

        audioCalculator = AudioCalculator(preferencesHelper.getFrequencySensibility())
        handler = Handler(Looper.getMainLooper())
    }

    fun onResume() {
        recorder!!.start(getApplication())
    }

    fun onPause() {
        recorder!!.stop()
    }
}