package com.bangkit.crealth.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.crealth.data.chatbot.DiagnosisRepository
import com.bangkit.crealth.data.chatbot.OptionDynamic
import com.bangkit.crealth.data.chatbot.SymptomsMap
import com.bangkit.crealth.data.model.Option
import com.bangkit.crealth.data.response.DiagnosisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiagnosisViewModel(private val repository: DiagnosisRepository) : ViewModel() {

    private val _diagnosisResult = MutableLiveData<DiagnosisResponse>()
    val diagnosisResult: LiveData<DiagnosisResponse> get() = _diagnosisResult

    private val _options = MutableLiveData<List<Option>>()
    val options: LiveData<List<Option>> get() = _options

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOptions() {
        _options.value = OptionDynamic.options
    }

    fun submitSymptoms(symptoms: SymptomsMap) {
        repository.submitSymptoms(symptoms).enqueue(object : Callback<DiagnosisResponse> {
            override fun onResponse(call: Call<DiagnosisResponse>, response: Response<DiagnosisResponse>) {
                if (response.isSuccessful) {
                    _diagnosisResult.value = response.body()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DiagnosisResponse>, t: Throwable) {
                _error.value = t.message
            }
        })
    }
}
