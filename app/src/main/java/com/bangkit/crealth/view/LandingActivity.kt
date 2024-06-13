package com.bangkit.crealth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.crealth.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLandingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val successMessage = intent.getStringExtra("successMessage")
        if (successMessage != null) {
            showToast(successMessage)
            intent.removeExtra("successMessage")
        }

        binding.btnStarted.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.toLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}