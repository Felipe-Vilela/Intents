package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    
    companion object Constants{
        const val NEW_ACTIVITY_REQUEST_CODE = 0
        const val EXTRA_PARAMETER = "EXTRA_PARAMETER"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.toolbarIn.toolbar.apply {
            subtitle = localClassName
            setSupportActionBar(this)
        }

        amb.entrarParametroBt.setOnClickListener{
            Intent(this@MainActivity, NewActivity::class.java).apply {
                startActivityForResult(this@apply, NEW_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            data?.getStringExtra(EXTRA_PARAMETER).let { parametro ->
                amb.parametroTv.text = parametro
            }
        }
    }
}