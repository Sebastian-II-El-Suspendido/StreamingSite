package com.example.streamingsite

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.streamingsite.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  binding.editTextEmail.setHintTextColor(Color.parseColor("#332F2C"))
        binding.editTextEmail.setHintTextColor(Color.RED)
        binding.editTextPassword.setHintTextColor(Color.parseColor("#332F2C"))
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, PantallaMain::class.java)
            startActivity(intent)
        }
        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }






























        fun validarContrasena(contrasena: String, confirm: String,context: Context) {
            val longitudValida = contrasena.length in 6..20
            val tieneMayuscula = contrasena.any { it.isUpperCase() }
            val tieneMinuscula = contrasena.any { it.isLowerCase() }
            val tieneNumero = contrasena.any { it.isDigit() }
            val tieneCaracterEspecial = contrasena.any { it in "!@#$%^&*()-_=+[]{},.<>?/;:'\"\\|`~" }

            when {
                !longitudValida -> Toast.makeText(context, "La contraseña debe tener entre 6 y 20 caracteres.", Toast.LENGTH_LONG).show()
                !tieneMayuscula -> Toast.makeText(context, "La contraseña debe contener al menos una mayúscula.", Toast.LENGTH_LONG).show()
                !tieneMinuscula -> Toast.makeText(context, "La contraseña debe contener al menos una minúscula.", Toast.LENGTH_LONG).show()
                !tieneNumero -> Toast.makeText(context, "La contraseña debe contener al menos un número.", Toast.LENGTH_LONG).show()
                !tieneCaracterEspecial ->Toast.makeText(context, "La contraseña debe contener al menos un carácter especial.", Toast.LENGTH_LONG).show()
                contrasena!=confirm -> Toast.makeText(context,"Las contraseñas deben coincidir", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context,"Usuario creado exitosamente", Toast.LENGTH_LONG).show()
            }





        }
    }
}