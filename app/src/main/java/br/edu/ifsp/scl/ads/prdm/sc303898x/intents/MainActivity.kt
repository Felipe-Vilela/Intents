package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var narl: ActivityResultLauncher<Intent>

    companion object Constants{
        const val NEW_ACTIVITY_REQUEST_CODE = 0
        const val EXTRA_PARAMETER = "EXTRA_PARAMETER"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        narl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {resultado ->
            if ( resultado.resultCode == RESULT_OK) {
                resultado.data?.getStringExtra(EXTRA_PARAMETER).let { parametro ->
                    amb.parametroTv.text = parametro
                }
            }
        }

        amb.toolbarIn.toolbar.apply {
            subtitle = localClassName
            setSupportActionBar(this)
        }

        amb.entrarParametroBt.setOnClickListener{
            Intent(this@MainActivity, NewActivity::class.java).apply {
                putExtra(EXTRA_PARAMETER, amb.parametroTv.text.toString())
                narl.launch(this)
            }
        }
    }
}