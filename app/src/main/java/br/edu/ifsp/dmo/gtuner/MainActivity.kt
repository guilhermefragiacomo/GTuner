package br.edu.ifsp.dmo.gtuner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.edu.ifsp.dmo.gtuner.ui.view.fragments.SheetFragment
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.MainViewModel
import br.edu.ifsp.dmo.gtuner.ui.viewmodel.MainViewModelFactory
import br.edu.ifsp.dmo.gtuner.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.gtuner.ui.view.fragments.TunerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val PERMISSOES_REQUERIDAS = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )
    private val REQUEST_PERMISSION_CODE = 101

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
                R.id.nav_sheets -> replaceFragment(SheetFragment())
                R.id.nav_settings -> replaceFragment(SheetFragment())
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
}