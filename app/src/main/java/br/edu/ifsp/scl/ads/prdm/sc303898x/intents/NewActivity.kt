package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.MainActivity.Constants.EXTRA_PARAMETER
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.databinding.ActivityNewBinding

class NewActivity : AppCompatActivity() {

    private val anb: ActivityNewBinding by lazy {
        ActivityNewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(anb.root)

        anb.toolbarIn.toolbar.apply {
            subtitle = localClassName
            setSupportActionBar(this)
        }

        anb.enviarParametroBt.setOnClickListener{
            val parametro = anb.parametroEt.text.toString()
            val intent = Intent()
            intent.putExtra(EXTRA_PARAMETER, parametro)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}