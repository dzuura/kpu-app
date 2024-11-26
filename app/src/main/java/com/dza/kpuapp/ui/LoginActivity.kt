package com.dza.kpuapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dza.kpuapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener {
                val username = editUsername.text.toString() // Ambil input username
                val password = editPassword.text.toString() // Ambil input password

                // Cek apakah input kosong
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Mohon isi semua data",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Validasi username dan password
                if (isValidUsernamePassword()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java)) // Navigasi ke MainActivity
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username atau password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Fungsi untuk validasi username dan password
    private fun isValidUsernamePassword(): Boolean {
        val username = "Daffa" // Username yang valid
        val password = "admin1234" // Password yang valid
        val inputUsername = binding.editUsername.text.toString() // Input username
        val inputPassword = binding.editPassword.text.toString() // Input password
        return username == inputUsername && password == inputPassword // Cek kesesuaian
    }
}