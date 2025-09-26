package br.edu.ifsp.scl.ads.prdm.sc303898x.intents

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.intents.databinding.ActivityMainBinding
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var narl: ActivityResultLauncher<Intent>

    companion object Constants{
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.view_mi -> {
                val url = amb.parametroTv.text.toString().toUri()
                val navegadorIntent = Intent(ACTION_VIEW, url)
                startActivity(navegadorIntent)

                amb.parametroTv.text.toString().let{urlTexto ->
                    Uri.parse(urlTexto).let { url->
                        Intent(ACTION_VIEW, url).apply {
                            startActivity(this)
                        }
                    }
                }

                true }
            R.id.call_mi -> { true }
            R.id.dial_mi -> { true }
            R.id.pick_mi -> { true }
            R.id.chosser_mi -> { true }
            else -> { false }
        }
    }

}