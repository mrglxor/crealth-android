package com.bangkit.crealth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.crealth.R
import com.bangkit.crealth.data.factory.ViewModelFactory
import com.bangkit.crealth.data.model.RegisterModel
import com.bangkit.crealth.data.user.UserModel
import com.bangkit.crealth.data.viewmodel.RegisterViewModel
import com.bangkit.crealth.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registerResult.observe(this) { response ->
            response?.let {
                if(it.message != null ){
                    val intentRegister = Intent(this, LandingActivity::class.java).apply {
                        putExtra("successMessage", response.message)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intentRegister)
                    finish()
                }else{
                    showMessageDialog(response.error!!)
                    binding.btnRegister.text =  getString(R.string.error)
                }
            }
        }

        val inputLayoutEmail = binding.customEmailTextInputLayout
        val inputLayoutPassword = binding.customPasswordTextInputLayout
        val inputLayoutName = binding.customNameTextInputLayout

        fun updateSignupButtonState() {
            val isNameValid = inputLayoutName.getName().length >= 4
            val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(inputLayoutEmail.getEmail()).matches()
            val isPasswordValid = inputLayoutPassword.getPassword().length >= 8

            val isNameErrorEmpty = inputLayoutName.error.isNullOrEmpty()
            val isEmailErrorEmpty = inputLayoutEmail.error.isNullOrEmpty()
            val isPasswordErrorEmpty = inputLayoutPassword.error.isNullOrEmpty()

            binding.btnRegister.isEnabled = isNameValid && isEmailValid && isPasswordValid && isNameErrorEmpty && isEmailErrorEmpty && isPasswordErrorEmpty
        }

        inputLayoutName.setTextChangeListener {
            updateSignupButtonState()
        }

        inputLayoutPassword.setTextChangeListener {
            updateSignupButtonState()
        }

        inputLayoutEmail.setEmailFormatChangeListener {
            updateSignupButtonState()
        }

        binding.btnRegister.setOnClickListener {
            val name = inputLayoutName.getName()
            val email = inputLayoutEmail.getEmail()
            val password = inputLayoutPassword.getPassword()

            binding.btnRegister.isEnabled = false
            binding.btnRegister.text = getString(R.string.loading)

            viewModel.register(RegisterModel(name,email,password),this)
        }
    }

    private fun showMessageDialog(responseMessage: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Message")
            setIcon(R.mipmap.icon_launcher)
            setMessage(responseMessage)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}