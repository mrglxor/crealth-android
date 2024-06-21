package com.bangkit.crealth.data.forum

import android.content.Context
import com.bangkit.crealth.data.api.ApiService
import com.bangkit.crealth.data.api.Injection
import com.bangkit.crealth.data.model.PostModel
import com.bangkit.crealth.data.response.PostResponse
import com.bangkit.crealth.data.response.PublicResponse
import com.bangkit.crealth.data.user.UserModel
import com.bangkit.crealth.data.user.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

class ForumRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun createPost(data: PostModel,context: Context): PostResponse {
        val apiService = Injection.provideForumRepository(context).apiService
        return try {
            apiService.createPost(data)
        }catch (e: HttpException){
            val errorResponse = parseErrorBody(e.response()?.errorBody())
            PostResponse(error = errorResponse ?: "Unknown Error!")
        } catch (e: IOException) {
            PostResponse(error = "No internet connection")
        }catch (e: Exception) {
            PostResponse(error = "Server Error!")
        }
    }

    suspend fun getPost(context: Context): PublicResponse {
        val apiService = Injection.provideForumRepository(context).apiService
        return try {
            apiService.getPost()
        }catch (e: HttpException){
            PublicResponse(error = "Unknown Error!")
        } catch (e: IOException) {
            PublicResponse(error = "No internet connection")
        }catch (e: Exception) {
            PublicResponse(error = "Server Error!")
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    private fun parseErrorBody(errorBody: ResponseBody?): String? {
        return try {
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody?.charStream(), PostResponse::class.java)
            errorResponse.error
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        @Volatile
        private var instance: ForumRepository? = null

        fun getInstance(apiService: ApiService,userPreference: UserPreference): ForumRepository =
            instance ?: synchronized(this) {
                instance ?: ForumRepository(apiService, userPreference).also { instance = it }
            }
    }
}

