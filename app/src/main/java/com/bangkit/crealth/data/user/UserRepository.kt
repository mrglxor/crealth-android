package com.bangkit.crealth.data.user

import android.content.Context
import com.bangkit.crealth.data.api.ApiService
import com.bangkit.crealth.data.api.Injection
import com.bangkit.crealth.data.model.LoginModel
import com.bangkit.crealth.data.model.RegisterModel
import com.bangkit.crealth.data.response.LoginResponse
import com.bangkit.crealth.data.response.RegisterResponse
import com.bangkit.crealth.data.response.toLoginResponse
import com.bangkit.crealth.data.response.toRegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun register(user: RegisterModel, context: Context): RegisterResponse {
        val apiService = Injection.provideRepository(context).apiService
        return try {
            apiService.register(user)
        }catch (e: HttpException){
            e.toRegisterResponse()
        } catch (e: IOException) {
            toRegisterResponse()
        }catch (e: Exception) {
            e.toRegisterResponse()
        }
    }

    suspend fun login(user: LoginModel, context: Context): LoginResponse {
        val apiService = Injection.provideRepository(context).apiService
        return try {
            apiService.login(user)
        } catch (e: HttpException) {
            e.toLoginResponse()
        } catch (e: IOException) {
            toLoginResponse()
        }catch (e: Exception) {
            e.toLoginResponse()
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.removeSession()
    }


    companion object {
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService,userPreference)
            }.also { instance = it }
    }
}