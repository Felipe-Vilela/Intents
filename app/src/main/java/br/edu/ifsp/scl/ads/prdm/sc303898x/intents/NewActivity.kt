package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}