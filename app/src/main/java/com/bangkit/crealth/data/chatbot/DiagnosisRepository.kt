package com.bangkit.crealth.data.chatbot

import com.bangkit.crealth.data.api.PredictApiService
import com.bangkit.crealth.data.response.DiagnosisResponse
import retrofit2.Call

class DiagnosisRepository(private val predictApiService: PredictApiService) {

    fun submitSymptoms(symptoms: SymptomsMap): Call<DiagnosisResponse> {
        return predictApiService.submitSymptoms(symptoms)
    }

    companion object {
        @Volatile
        private var instance: DiagnosisRepository? = null

        fun getInstance(predictApiService: PredictApiService): DiagnosisRepository =
            instance ?: synchronized(this) {
                instance ?: DiagnosisRepository(predictApiService).also { instance = it }
            }
    }
}