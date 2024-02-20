package com.example.streamingsite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.streamingsite.databinding.ActivityPantallaMainBinding
import com.example.streamingsite.databinding.ActivityPerfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val home = findViewById<ImageView>(R.id.iconologo)
        home.setOnClickListener{
            val intent = Intent(this, PantallaMain::class.java)
            startActivity(intent)
        }
        val user = Firebase.auth.currentUser
        val nombreUsuario = user?.displayName
        val correoUsuario = user?.email
        val passSize = intent.getIntExtra("PASS_SIZE",6)

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(correoUsuario?.substringBefore("@"))
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("UpdateProfile", "Nombre de usuario actualizado.")
            }
        }

        binding.textViewEmail.text = correoUsuario
        binding.textViewUser.text = correoUsuario?.substringBefore("@")
        binding.textViewPass.text= generarAsteriscos(passSize)

        val photoUrl = user?.photoUrl
        Glide.with(this)
            .load(photoUrl)
            .circleCrop()
            .into(binding.imagePerfil)


        binding.button5.setOnClickListener{
            val intent2 = Intent(this, Login::class.java)
            val auth=FirebaseAuth.getInstance()
            auth.signOut()
            startActivity(intent2)
        }

        binding.button6.setOnClickListener{
            val intent3 = Intent(this, Personalizar::class.java)
            startActivity(intent3)
        }

    }



    private fun generarAsteriscos(cantidad: Int): String {
        return "*".repeat(cantidad)
    }

}