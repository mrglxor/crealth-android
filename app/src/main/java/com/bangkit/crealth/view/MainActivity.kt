package com.bangkit.crealth.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.bangkit.crealth.R
import com.bangkit.crealth.databinding.ActivityMainBinding
import com.bangkit.crealth.view.fragment.ArticleFragment
import com.bangkit.crealth.view.fragment.ForumFragment
import com.bangkit.crealth.view.fragment.HomeFragment
import com.bangkit.crealth.view.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(500)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val fragment = ForumFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            if(!isLoading) {
                when(menuItem.itemId) {
                    R.id.nav_home -> {
                        loadFragment(HomeFragment())
                        true
                    }
                    R.id.nav_forum -> {
                        loadFragment(ForumFragment())
                        true
                    }
                    R.id.nav_article -> {
                        loadFragment(ArticleFragment())
                        true
                    }
                    R.id.nav_profile -> {
                        loadFragment(ProfileFragment())
                        true
                    }
                    else -> false
                }
            }else{
                false
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun loadFragment(fragment: Fragment) {
        isLoading = true
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
        isLoading = false
    }
}