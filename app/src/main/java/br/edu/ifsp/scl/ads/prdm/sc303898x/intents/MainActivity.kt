package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.ACTION_CHOOSER
import android.content.Intent.ACTION_DIAL
import android.content.Intent.ACTION_PICK
import android.content.Intent.ACTION_VIEW
import android.content.Intent.EXTRA_INTENT
import android.content.Intent.EXTRA_TITLE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.databinding.ActivityMainBinding
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object Constants {
        const val EXTRA_PARAMETER = "EXTRA_PARAMETER"
    }

    private lateinit var narl: ActivityResultLauncher<Intent>
    private lateinit var callPermission: ActivityResultLauncher<String>
    private lateinit var pickImageArl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        amb.toolbarIn.toolbar.apply {
            subtitle = localClassName
            setSupportActionBar(this)
        }

        narl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.getStringExtra(EXTRA_PARAMETER).let { parametro ->
                    amb.parametroTv.text = parametro
                }
            }
        }

        callPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGranted ->
            if (permissionGranted) {
                callDialPhone(true)
            } else {
                Toast.makeText(this@MainActivity, "Impossível discar sem a permissão", Toast.LENGTH_SHORT).show()
            }
        }

        pickImageArl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if (result.resultCode == RESULT_OK){
                result.data?.data.let {uri ->
                    startActivity(Intent(ACTION_VIEW, uri))
                }
            }

        }

        amb.entrarParametroBt.setOnClickListener {
            Intent(this@MainActivity, NewActivity::class.java).apply {
                putExtra(EXTRA_PARAMETER, amb.parametroTv.text.toString())
                narl.launch(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun browserIntent(): Intent{
        amb.parametroTv.text.toString().let() { urlText ->
            urlText.toUri().let { url ->
                Intent(ACTION_VIEW, url).apply {
                    return this
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.view_mi -> {
                startActivity(browserIntent())
                true
            }
            R.id.call_mi -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(CALL_PHONE) == PERMISSION_GRANTED) {
                        callDialPhone(true)
                    } else {
                        callPermission.launch(CALL_PHONE)
                    }
                } else {
                    callDialPhone(true)
                }

                true
            }
            R.id.dial_mi -> {
                callDialPhone(false)
                true
            }
            R.id.pick_mi -> {
                var imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
                var pickIntent = Intent(ACTION_PICK)
                pickIntent.setDataAndType(imageDir.toUri(), "image/*")
                pickImageArl.launch(pickIntent)
                true
            }
            R.id.chosser_mi -> {
                startActivity(Intent(ACTION_CHOOSER).apply {
                    putExtra(EXTRA_TITLE, "Escolha seu navegador")
                    putExtra(EXTRA_INTENT, browserIntent())
                })

                true}
            else -> false
        }
    }

    private fun callDialPhone(call: Boolean) {
        amb.parametroTv.text.toString().let() { phoneNumber ->
            ("tel: $phoneNumber").toUri().let { url ->
                Intent(if (call) ACTION_CALL else ACTION_DIAL, url).apply {
                    startActivity(this)
                }
            }
        }
    }
}