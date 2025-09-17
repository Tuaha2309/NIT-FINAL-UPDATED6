package com.stylo.nit.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.stylo.nit.dashboard.dashboard
import com.stylo.nit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(username, password)
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginState.Loading -> {
                    binding.btnLogin.isEnabled = false
                }

                is LoginViewModel.LoginState.Success -> {
                    val keypass = state.response.keypass

                    // Save keypass
                    val prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                    prefs.edit().putString("KEYPASS", keypass).apply()

                    binding.btnLogin.isEnabled = true

                    // Redirect to new activity
                    val intent = Intent(this, dashboard::class.java)
                    startActivity(intent)
                    finish() // Optional: to prevent going back to login with back button
                }

                is LoginViewModel.LoginState.Error -> {
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}