package com.ahrorovk.testmangofzco.domain.use_cases

import android.util.Log
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.domain.model.requestBody.UpdateUserInfoBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.ErrorResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UpdateUserInfoResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserRegistrationResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(token:String,updateUserInfoBody: UpdateUserInfoBody): Flow<Resource<UpdateUserInfoResponse>> =
        flow {
            try {
                emit(Resource.Loading<UpdateUserInfoResponse>())
                val response = repository.updateUserInfo("Bearer $token",updateUserInfoBody)
                emit(Resource.Success<UpdateUserInfoResponse>(response))
            } catch (e: HttpException) {
                val gson = Gson()
                lateinit var jsonString:String;
                lateinit var response: ErrorResponse
                e.response()?.errorBody()?.let {
                    jsonString = it.string()
                    Log.e("GSON", jsonString)
                    response = gson.fromJson(jsonString, ErrorResponse::class.java)
                }
                emit(
                    Resource.Error<UpdateUserInfoResponse>(
                        response.detail[0].msg ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<UpdateUserInfoResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<UpdateUserInfoResponse>("${e.message}"))
            }
        }
}