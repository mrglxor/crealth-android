package com.bangkit.crealth.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bangkit.crealth.R
import com.bangkit.crealth.data.adapter.ViewPagerAdapter
import com.bangkit.crealth.data.chatbot.DepthPageTransformer
import com.bangkit.crealth.data.chatbot.SymptomsMap
import com.bangkit.crealth.data.factory.DiagnosisViewModelFactory
import com.bangkit.crealth.data.provider.DiagnosisRepositoryProvider
import com.bangkit.crealth.data.viewmodel.DiagnosisViewModel
import com.bangkit.crealth.databinding.ActivityChatbotBinding
import com.bangkit.crealth.view.fragment.DiagnosisBottomSheetFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val viewModel: DiagnosisViewModel by viewModels {
        DiagnosisViewModelFactory(DiagnosisRepositoryProvider.buildRepository())
    }

    private val selectedSymptoms = mutableSetOf<String>()
    private var bottomSheetFragment: DiagnosisBottomSheetFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewPager()
        setupSubmitButton()
        observeViewModel()

        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dotsIndicator)
        dotsIndicator.setViewPager2(binding.viewPager)

        viewModel.fetchOptions()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(emptyList()) { option, isChecked ->
            if (isChecked) {
                selectedSymptoms.add(option.text)
            } else {
                selectedSymptoms.remove(option.text)
            }
            binding.btnSubmit.isEnabled = selectedSymptoms.size >= 3
        }

        binding.viewPager.apply {
            adapter = viewPagerAdapter
            setPageTransformer(DepthPageTransformer())
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmit.setOnClickListener {
            if (selectedSymptoms.size < 3) {
                Toast.makeText(this, "Anda harus memilih minimal 3 gejala", Toast.LENGTH_LONG).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val formattedSymptoms = selectedSymptoms.map { it.lowercase().replace(" ", "_") }
                viewModel.submitSymptoms(SymptomsMap(formattedSymptoms))
            }
        }
    }

    private fun observeViewModel() {
        viewModel.diagnosisResult.observe(this) { result ->
            binding.tvError.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            if (bottomSheetFragment == null) {
                bottomSheetFragment = DiagnosisBottomSheetFragment()
            }
            bottomSheetFragment?.updateData(
                symptoms = "Gejala: ${formatText(result.symptoms)}",
                diseases = "Penyakit: ${result.disease}",
                description = "Deskripsi: ${result.description}",
                precautions = "Tindakan pencegahan: ${result.precautions}"
            )
            bottomSheetFragment?.show(supportFragmentManager, bottomSheetFragment?.tag)
        }

        viewModel.error.observe(this) { error ->
            binding.progressBar.visibility = View.GONE
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = error
            binding.btnSubmit.isEnabled = false
        }

        viewModel.options.observe(this, Observer { options ->
            viewPagerAdapter.updateOptions(options)
        })
    }

    private fun formatText(text: String?): String? {
        return text?.split("_")?.joinToString(" ") { it.capitalize() }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
