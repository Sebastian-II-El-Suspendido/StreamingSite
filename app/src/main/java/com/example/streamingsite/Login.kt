package com.example.streamingsite

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}