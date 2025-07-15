package br.edu.ifsp.dmo.gtuner.ui.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

class CameraHelper(
    activity: FragmentActivity,
    private val callback: Callback
) {
    interface Callback {
        fun onFotoRecebida(bitmap: Bitmap)
    }
    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap : Bitmap?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bitmap = result.data?.extras?.getParcelable(
                    "data",
                    Bitmap::class.java)
            } else {
                @Suppress("DEPRECATION")
                bitmap = result.data?.extras?.get("data") as? Bitmap
            }
            bitmap?.let {
                val copia = it.copy(Bitmap.Config.ARGB_8888, true)
                callback.onFotoRecebida(copia)
            }
        }
    }
    fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcher.launch(intent)
    }
}