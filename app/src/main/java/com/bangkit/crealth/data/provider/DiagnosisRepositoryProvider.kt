package com.bangkit.crealth.data.provider

import com.bangkit.crealth.data.api.ApiConfig
import com.bangkit.crealth.data.chatbot.DiagnosisRepository

object DiagnosisRepositoryProvider {

    fun buildRepository(): DiagnosisRepository {
        val predictApiService = ApiConfig.getPredictApiService()
        return DiagnosisRepository(predictApiService)
    }
}