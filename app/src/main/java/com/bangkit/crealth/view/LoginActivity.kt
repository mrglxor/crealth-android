package com.bangkit.crealth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.crealth.R
import com.bangkit.crealth.data.factory.ViewModelFactory
import com.bangkit.crealth.data.model.LoginModel
import com.bangkit.crealth.data.user.UserModel
import com.bangkit.crealth.data.viewmodel.LoginViewModel
import com.bangkit.crealth.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getSession().observe(this) {session ->
            if(session.isLogin && session.token.isNotBlank()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else {
                binding = ActivityLoginBinding.inflate(layoutInflater)
                setContentView(binding.root)

                setupView()
                setupAction()

                viewModel.loginResult.observe(this) { response ->
                    response?.let {
                        if (!it.error.isNullOrEmpty()) {
                            showErrorDialog(it.error)
                        } else {
                            response.data?.let { user ->
                                val data = UserModel(
                                    user.name!!,
                                    user.id!!,
                                    "",
                                    true,
                                    user.rememberToken!!
                                )
                                viewModel.saveSession(data)

                                val successMessage = it.message ?: "Login successful"
                                val intentLogin = Intent(this, MainActivity::class.java).apply {
                                    putExtra("successMessage", successMessage)
                                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                startActivity(intentLogin)
                                finish()
                            }
                            Toast.makeText(this,"an error occurred on the server!",Toast.LENGTH_LONG).show()
                            binding.btnLogin.text = getString(R.string.error)
                        }
                    }
                }
            }
        }
        
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Message!")
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
        binding.btnLogin.text = getString(R.string.error)
    }

    private fun setupAction() {
        val inputLayoutEmail = binding.customEmailTextInputLayout
        val inputLayoutPassword = binding.customPasswordTextInputLayout

        fun updateSigninButtonState() {
            val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(inputLayoutEmail.getEmail()).matches()
            val isPasswordValid = inputLayoutPassword.getPassword().length >= 8

            val isEmailErrorEmpty = inputLayoutEmail.error.isNullOrEmpty()
            val isPasswordErrorEmpty = inputLayoutPassword.error.isNullOrEmpty()

            binding.btnLogin.isEnabled = isEmailValid && isPasswordValid && isEmailErrorEmpty && isPasswordErrorEmpty
        }

        inputLayoutPassword.setTextChangeListener {
            updateSigninButtonState()
        }

        inputLayoutEmail.setEmailFormatChangeListener {
            updateSigninButtonState()
        }

        binding.btnLogin.setOnClickListener {
            val email = inputLayoutEmail.getEmail()
            val password = inputLayoutPassword.getPassword()

            binding.btnLogin.text = getString(R.string.loading)
            binding.btnLogin.isEnabled = false

//            viewModel.login(LoginModel(email,password),this)
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}