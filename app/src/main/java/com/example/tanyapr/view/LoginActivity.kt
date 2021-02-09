package com.example.tanyapr.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tanyapr.databinding.ActivityLoginBinding
import com.example.tanyapr.helper.Helper
import com.example.tanyapr.view.admin.activity.AdminActivity
import com.example.tanyapr.view.user.guru.activity.GuruActivity
import com.example.tanyapr.view.user.siswa.activity.SiswaActivity
import com.example.tanyapr.viewmodel.LoginViewModel
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, Helper.viewModelFactory {
            LoginViewModel()
        })[LoginViewModel::class.java]

        val uName = intent.getStringExtra("username")
        uName?.let { binding.tieUsername.setText(it) }

        viewModel.getState().observe(this, {
            updateUi(it)
        })

        binding.btnLogin.setOnClickListener {
            val username = binding.tieUsername.text.toString().trim()
            val password = binding.tiePassword.text.toString().trim()
            if(viewModel.isValid(username, password))
                viewModel.login(username, password)
        }

        binding.btnRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
                ActivityCompat.finishAffinity(this@LoginActivity)
            }
        }
    }

    private fun updateUi(state: LoginViewModel.LoginState) {
        when(state){
            is LoginViewModel.LoginState.Loading -> {
                when (state.isLoading) {
                    true -> {
                        binding.btnLogin.isEnabled = false
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.btnLogin.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
            is LoginViewModel.LoginState.Success -> {
                val intent = when(state.user.role) {
                    "guru" -> Intent(this, GuruActivity::class.java)
                    "siswa" -> Intent(this, SiswaActivity::class.java)
                    else -> Intent(this, AdminActivity::class.java)
                }
                state.user.id_mata_pelajaran?.let {
                    FirebaseMessaging.getInstance().subscribeToTopic(it.toString())
                }
                Helper.setToken(this, state.user)
                startActivity(intent)
                ActivityCompat.finishAffinity(this)
            }
            is LoginViewModel.LoginState.Fail -> {
                Helper.snackBar(binding.root, state.message)
            }
            is LoginViewModel.LoginState.Invalid -> {
                val username = state.username
                username?.let { binding.tilUsername.error = username }
                val password = state.password
                password?.let { binding.tilPassword.error = password}
            }
            is LoginViewModel.LoginState.Error -> {
                Helper.snackBar(binding.root, state.message)
            }
            LoginViewModel.LoginState.Reset -> {
                binding.tilUsername.error = null
                binding.tilPassword.error = null
            }
        }
    }

}