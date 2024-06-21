package com.bangkit.crealth.view.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkit.crealth.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DiagnosisBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    private var symptoms: String? = null
    private var diseases: String? = null
    private var description: String? = null
    private var precautions: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        binding.btnCopy.setOnClickListener {
            copyToClipboard()
        }
    }

    private fun copyToClipboard() {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Diagnosis Result", """
            $symptoms
            
            $diseases
            
            
            $description
            
            $precautions
        """.trimIndent())
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Hasil diagnosis telah disalin ke clipboard", Toast.LENGTH_SHORT).show()
        binding.btnCopy.isEnabled = false
        val text = "Berhasil Disalin"
        binding.btnCopy.text = text
    }

    private fun updateUI() {
        binding.tvSymptoms.text = formatText(symptoms)
        binding.tvDiseases.text = diseases
        binding.tvDescription.text = description
        binding.tvPrecautions.text = precautions
    }

    private fun formatText(text: String?): String? {
        return text?.split("_")?.joinToString(" ") { it.capitalize() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateData(symptoms: String, diseases: String, description: String, precautions: String) {
        this.symptoms = symptoms
        this.diseases = diseases
        this.description = description
        this.precautions = precautions
        if (_binding != null) {
            updateUI()
        }
    }
}
