package es.ua.eps.contentproviders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import es.ua.eps.contentproviders.databinding.ActivityUserDataBinding

class UserData : AppCompatActivity() {
    private lateinit var bindings: ActivityUserDataBinding

    companion object {
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_NOMBRE_COMPLETO = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityUserDataBinding.inflate(layoutInflater)

        with(bindings) {
            setContentView(root)

            nameTextView.text = intent.getStringExtra(COLUMN_NOMBRE_COMPLETO)
            usernameTextView.text = intent.getStringExtra(COLUMN_USERNAME)

            backButton.setOnClickListener {
                finish()
            }
        }
    }
}