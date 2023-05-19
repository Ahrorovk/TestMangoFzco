package com.ahrorovk.testmangofzco.domain.use_cases

import android.util.Log
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.data.ApplicationApi
import com.ahrorovk.testmangofzco.di.AppModule
import com.ahrorovk.testmangofzco.domain.model.requestBody.UserBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.SendAuthCodeResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserRegistrationResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class UserRegistrationUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(user: UserBody): Flow<Resource<UserRegistrationResponse>> =
        flow {
            try {
                emit(Resource.Loading<UserRegistrationResponse>())
                val responses = repository.registerUser(user)
                emit(Resource.Success<UserRegistrationResponse>(responses))
            } catch (e: HttpException) {
                val gson = Gson()
                lateinit var jsonString:String;
                lateinit var response: UserRegistrationResponse
                e.response()?.errorBody()?.let {
                    jsonString = it.string()
                    Log.e("GSON", jsonString)
                    response = gson.fromJson(jsonString, UserRegistrationResponse::class.java)
                }
                emit(
                    Resource.Error<UserRegistrationResponse>(
                        response.detail.message ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<UserRegistrationResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<UserRegistrationResponse>("${e.message}"))
            }
        }

}