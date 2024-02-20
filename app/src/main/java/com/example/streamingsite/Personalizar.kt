package com.example.streamingsite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage

class Personalizar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar)

        val volumeIcons = Array(24) { index -> "Iconos/volume_$index.png" }
        val storageReference = Firebase.storage.reference

        val user = Firebase.auth.currentUser


        for (i in volumeIcons.indices) {
            val imageViewId = resources.getIdentifier("imageView${i + 1}", "id", packageName)
            val imageView = findViewById<ImageView>(imageViewId)
            val imagenUrl = volumeIcons[i]

            Glide.with(this)
                .load(imagenUrl)
                .placeholder(R.drawable.volume_0)
                .error(R.drawable.volume_24)
                .into(imageView)
        }
            }
        }


