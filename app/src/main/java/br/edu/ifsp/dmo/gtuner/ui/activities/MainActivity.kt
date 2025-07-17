package br.edu.ifsp.dmo.gtuner.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo.gtuner.R
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import br.edu.ifsp.dmo.gtuner.ui.view.fragments.SheetFragment
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.MainViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.MainViewModelFactory
import br.edu.ifsp.dmo.gtuner.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.gtuner.ui.util.CameraHelper
import br.edu.ifsp.dmo.gtuner.ui.view.fragments.SettingsFragment
import br.edu.ifsp.dmo.gtuner.ui.view.fragments.TunerFragment

class MainActivity : AppCompatActivity(), CameraHelper.Callback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val PERMISSOES_REQUERIDAS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val REQUEST_PERMISSION_CODE = 101

    private lateinit var cameraHelper: CameraHelper
    private lateinit var originalBitmap: Bitmap
    private val REQUEST_CAMERA_CODE = 1001
    private val REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!temPermissoesNecessarias()) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSOES_REQUERIDAS,
                REQUEST_PERMISSION_CODE)
        }

        cameraHelper = CameraHelper(this, this)

        val factory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        replaceFragment(TunerFragment())

        setupListeners()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun setupListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_tuner -> replaceFragment(TunerFragment())
                R.id.nav_sheets -> replaceFragment(SheetFragment(this))
                R.id.nav_settings -> replaceFragment(SettingsFragment())
                else -> {

                }
            }
            true
        }
    }

    private fun temPermissoesNecessarias(): Boolean {
        return PERMISSOES_REQUERIDAS.all {
            ContextCompat.checkSelfPermission(this, it) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED
                }) {
                Toast.makeText(
                    this,
                    "Permissões necessárias não concedidas. O app pode não funcionar corretamente.",
                            Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun savePhotoSheet(name: String, author: String, arrangment: String) {
        viewModel.addSheet(name, author, arrangment, originalBitmap)
    }

    override fun onFotoRecebida(bitmap: Bitmap) {
        originalBitmap = bitmap
    }

    fun takePhoto() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            cameraHelper.takePhoto()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_CODE
            )
        }
    }
}