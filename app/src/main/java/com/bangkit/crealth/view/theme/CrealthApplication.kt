package com.bangkit.crealth.view.theme

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class CrealthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}