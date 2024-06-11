package com.bangkit.crealth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bangkit.crealth.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLandingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        Thread.sleep(500)
        installSplashScreen()
        setContentView(binding.root)

        binding.btnStarted.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.toLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}