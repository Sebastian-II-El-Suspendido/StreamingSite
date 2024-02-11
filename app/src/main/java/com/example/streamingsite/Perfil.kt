package com.example.streamingsite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import com.example.streamingsite.databinding.ActivityPerfilBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageButton.setOnClickListener {
            val intent = Intent(this, PantallaMain::class.java)
            startActivity(intent)
        }

    }
}