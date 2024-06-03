package com.example.myapplicationtareas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvForgotPassword: TextView = findViewById(R.id.tvForgotPassword)
        val tvRegister: TextView = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            // Aquí deberías verificar las credenciales del usuario
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvForgotPassword.setOnClickListener {
            // Navegar a la actividad de "Olvidaste tu contraseña"
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        tvRegister.setOnClickListener {
            // Navegar a la actividad de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}




