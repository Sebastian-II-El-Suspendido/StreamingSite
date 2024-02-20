package com.example.streamingsite

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.streamingsite.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSingIn : TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        email = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnSingIn = findViewById(R.id.btnSignIn)

        try {
            btnLogin.setOnClickListener{
                if (email.text.isNotEmpty() && password.text.isNotEmpty()){

                    loginUser(email.text.toString(),password.text.toString())

                } else {
                    Toast.makeText(this, "Los campos requeridos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (e: Exception) {
            Log.d(TAG, "Error en la autentificacion del usuario")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("There is a mistake on the athentication")
            builder.setPositiveButton("OK",null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


        try {
            btnSingIn.setOnClickListener{
                val intent = Intent(this, Registro::class.java)
                startActivity(intent)
            }
        }
        catch (e: Exception) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("There is a mistake for Sign In.")
            builder.setPositiveButton("OK",null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }
    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "El usuario no existe.", Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "La contraseña es incorrecta.", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error de autenticación: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d(TAG, "Autenticación del usuario correcta.")
                    val intent = Intent(this, PantallaMain::class.java).apply {
                        putExtra("PASS_SIZE", password.length)
                    }
                    startActivity(intent)
                }
            }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}