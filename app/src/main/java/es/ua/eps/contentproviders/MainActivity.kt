package es.ua.eps.contentproviders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import es.ua.eps.contentproviders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding

    companion object {
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_NOMBRE_COMPLETO = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)

        with(bindings) {
            setContentView(root)

            buttonLogin.setOnClickListener {

            }

            buttonClose.setOnClickListener {
                finish()
            }
        }
    }
}