package es.ua.eps.contentproviders

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import es.ua.eps.contentproviders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindings: ActivityMainBinding

    companion object {
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_NOMBRE_COMPLETO = "nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityMainBinding.inflate(layoutInflater)

        with(bindings) {
            setContentView(root)

            val usersList = getUsers()

            Log.d("Users list", usersList.toString())

            buttonLogin.setOnClickListener {
                val enteredUsername = editTextUsername.text.toString()
                val enteredPassword = editTextPassword.text.toString()

                val matchingUser = usersList.find { user ->
                    user.getAsString(COLUMN_USERNAME)?.equals(enteredUsername, ignoreCase = true) == true &&
                    user.getAsString(COLUMN_PASSWORD)?.equals(enteredPassword) == true
                }

                if (matchingUser != null) {
                    val username = matchingUser.getAsString(COLUMN_USERNAME)
                    val nombreCompleto = matchingUser.getAsString(COLUMN_NOMBRE_COMPLETO)

                    val intent = Intent(this@MainActivity, UserData::class.java)
                    intent.putExtra(COLUMN_USERNAME, username)
                    intent.putExtra(COLUMN_NOMBRE_COMPLETO,nombreCompleto)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                    Toast.makeText(this@MainActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error usuario/password incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

            buttonClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun getUsers(): ArrayList<ContentValues> {
        val usersList = ArrayList<ContentValues>()

        val contentResolver: ContentResolver = this.contentResolver
        val uri: Uri = Uri.parse("content://es.ua.eps.sqliteapp/User")

        val cursor: Cursor? = contentResolver.query(uri, arrayOf("uid","username","password","email","nombre"),
            null, null, null)

        cursor?.use {
            val columnIndexUsername = it.getColumnIndex(COLUMN_USERNAME)
            val columnIndexPassword = it.getColumnIndex(COLUMN_PASSWORD)
            val columnIndexNombreCompleto = it.getColumnIndex(COLUMN_NOMBRE_COMPLETO)

            while (it.moveToNext()) {
                val username = if (columnIndexUsername != -1) it.getString(columnIndexUsername) else null
                val password = if (columnIndexPassword != -1) it.getString(columnIndexPassword) else null
                val nombre = if (columnIndexNombreCompleto != -1) it.getString(columnIndexNombreCompleto) else null

                val user = ContentValues().apply {
                    put(COLUMN_USERNAME, username)
                    put(COLUMN_PASSWORD, password)
                    put(COLUMN_NOMBRE_COMPLETO, nombre)
                }
                usersList.add(user)
            }
        }
        cursor?.close()

        return usersList
    }

}