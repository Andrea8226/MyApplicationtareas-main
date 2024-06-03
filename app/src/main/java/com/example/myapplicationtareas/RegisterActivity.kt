package com.example.myapplicationtareas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar Firebase Auth y Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val btnRegister: Button = findViewById(R.id.btnRegister)
        val tvBackToLogin: TextView = findViewById(R.id.tvBackToLogin)

        btnRegister.setOnClickListener {
            val etFirstName: EditText = findViewById(R.id.etFirstName)
            val etLastName: EditText = findViewById(R.id.etLastName)
            val etEmail: EditText = findViewById(R.id.etEmail)
            val etAddress: EditText = findViewById(R.id.etAddress)
            val etIdNumber: EditText = findViewById(R.id.etIdNumber)
            val etPhoneNumber: EditText = findViewById(R.id.etPhoneNumber)
            val etPassword: EditText = findViewById(R.id.etPassword)

            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()
            val address = etAddress.text.toString()
            val idNumber = etIdNumber.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val password = etPassword.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && idNumber.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password, firstName, lastName, address, idNumber, phoneNumber)
            } else {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvBackToLogin.setOnClickListener {
            // Volver a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(email: String, password: String, firstName: String, lastName: String, address: String, idNumber: String, phoneNumber: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val user = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email,
                        "address" to address,
                        "idNumber" to idNumber,
                        "phoneNumber" to phoneNumber
                    )
                    firestore.collection("users").document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Error al registrar usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
