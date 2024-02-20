package com.example.streamingsite

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.streamingsite.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        val storageReference = Firebase.storage.reference.child("Iconos/volume_0.png")

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            val defaultImageUrl = uri.toString()
            // Procede a establecer esta URL como la imagen de perfil del usuario
            updateUserProfile(defaultImageUrl)
        }.addOnFailureListener {
            // Manejar cualquier error aquí, como un fallback o mostrar un error
        }






        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim() // Asegúrate de tener este campo en tu UI

            // Primero, valida el correo
            if (!validarCorreo(email)) {
                Toast.makeText(this, "Por favor ingrese un correo válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Luego, valida la contraseña
            if (!validarContrasena(password, confirmPassword, this)) {
                // Aquí deberías informar al usuario específicamente qué validación de contraseña falló
                // Por simplicidad, mostramos un mensaje genérico
                Toast.makeText(this, "La contraseña no cumple con los requisitos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si ambas validaciones son exitosas, procede con el registro
            registerUser(email, password)
        }
    }


    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@Registro) { task ->
                if (task.isSuccessful) {
                    // Registration success
                    Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                    // Navigate to the next screen or finish activity
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "El correo electrónico ya está registrado.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Otro tipo de error de registro
                        Toast.makeText(this, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }



    }

    private fun updateUserProfile(imageUrl: String) {
        val user = Firebase.auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(imageUrl))
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // La imagen de perfil ha sido establecida exitosamente
                Log.d(TAG, "Imagen de perfil por defecto establecida.")
            } else {
                // Manejar el fallo en la actualización del perfil
                Log.d(TAG, "Error al establecer la imagen de perfil por defecto.")
            }
        }
    }

    fun validarCorreo(email: String):Boolean=Patterns.EMAIL_ADDRESS.matcher(email).matches()


    fun validarContrasena(contrasena: String, confirm: String, context: Context): Boolean {
        val longitudValida = contrasena.length in 6..20
        val tieneMayuscula = contrasena.any { it.isUpperCase() }
        val tieneMinuscula = contrasena.any { it.isLowerCase() }
        val tieneNumero = contrasena.any { it.isDigit() }
        val tieneCaracterEspecial = contrasena.any { it in "!@#$%^&*()-_=+[]{},.<>?/;:'\"\\|`~" }

        when {
            !longitudValida -> {
                Toast.makeText(context, "La contraseña debe tener entre 6 y 20 caracteres.", Toast.LENGTH_LONG).show()
                return false
            }
            !tieneMayuscula -> {
                Toast.makeText(context, "La contraseña debe contener al menos una mayúscula.", Toast.LENGTH_LONG).show()
                return false
            }
            !tieneMinuscula -> {
                Toast.makeText(context, "La contraseña debe contener al menos una minúscula.", Toast.LENGTH_LONG).show()
                return false
            }
            !tieneNumero -> {
                Toast.makeText(context, "La contraseña debe contener al menos un número.", Toast.LENGTH_LONG).show()
                return false
            }
            !tieneCaracterEspecial -> {
                Toast.makeText(context, "La contraseña debe contener al menos un carácter especial.", Toast.LENGTH_LONG).show()
                return false
            }
            contrasena != confirm -> {
                Toast.makeText(context, "Las contraseñas deben coincidir.", Toast.LENGTH_LONG).show()
                return false
            }
            else -> {
                // Si todas las validaciones pasan, no necesitas mostrar un Toast aquí.
                // Podrías manejar la lógica de éxito después de llamar a esta función, basado en su valor de retorno.
                return true
            }
        }
    }



}