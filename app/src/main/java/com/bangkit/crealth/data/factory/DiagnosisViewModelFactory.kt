package com.bangkit.crealth.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.crealth.data.chatbot.DiagnosisRepository
import com.bangkit.crealth.data.viewmodel.DiagnosisViewModel

class DiagnosisViewModelFactory(private val repository: DiagnosisRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiagnosisViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiagnosisViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}