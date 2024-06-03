package com.example.myapplicationtareas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val btnResetPassword: Button = findViewById(R.id.btnResetPassword)
        val tvBackToLogin: TextView = findViewById(R.id.tvBackToLogin)

        btnResetPassword.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Correo de restauración enviado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Error al enviar el correo de restauración: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }

        tvBackToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
