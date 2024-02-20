package com.example.streamingsite

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlin.random.Random

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var imageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val numeroAleatorio = Random.nextInt(0, 25)
        auth = FirebaseAuth.getInstance()
        val volumeIcons = Array(24) { index -> "Iconos/volume_$index.png" }
        val storageReference = Firebase.storage.reference
        imageReference = storageReference.child(volumeIcons[numeroAleatorio])


        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString()
                .trim() // Asegúrate de tener este campo en tu UI

            // Primero, valida el correo
            if (!validarCorreo(email)) {
                Toast.makeText(this, "Por favor ingrese un correo válido.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            // Luego, valida la contraseña
            if (!validarContrasena(password, confirmPassword, this)) {
                // Aquí deberías informar al usuario específicamente qué validación de contraseña falló
                // Por simplicidad, mostramos un mensaje genérico
                Toast.makeText(
                    this,
                    "La contraseña no cumple con los requisitos.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Si ambas validaciones son exitosas, procede con el registro
            val intent = Intent(this, Perfil::class.java).apply {
                putExtra("PASS_SIZE", password.length)
        }
            registerUser(email, password)
            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }
    }


    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@Registro) { task ->
                if (task.isSuccessful) {
                    // Usuario registrado exitosamente

                    // Extraer el nombre del usuario a partir del correo electrónico
                    val displayName = email.substringBefore("@")
                    // Obtener la URL de la imagen predeterminada
                    imageReference.downloadUrl.addOnSuccessListener { uri ->
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName) // Establecer el nombre del usuario
                            .setPhotoUri(uri) // Establecer la foto de perfil
                            .build()
                        // Actualizar el perfil del usuario con el nombre y la foto de perfil
                        user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Log.d("Registro", "Perfil de usuario actualizado con nombre y imagen predeterminada.")
                                // Aquí puedes continuar con la lógica post-registro, como mostrar un mensaje o navegar a otra Activity
                            }
                        }
                    }.addOnFailureListener {
                        // Manejar el caso de que no se pueda obtener la URL de la imagen predeterminada
                        Log.e("Registro", "No se pudo obtener la URL de la imagen predeterminada.", it)
                    }
                } else {
                    // Manejo de errores de registro
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "El correo electrónico ya está registrado.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
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