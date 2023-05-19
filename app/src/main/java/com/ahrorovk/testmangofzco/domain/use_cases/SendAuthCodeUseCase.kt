package com.ahrorovk.testmangofzco.domain.use_cases

import android.util.Log
import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.domain.model.requestBody.SendAuthCodeBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.SendAuthCodeErrorResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.SendAuthCodeResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(authCodeBody: SendAuthCodeBody): Flow<Resource<SendAuthCodeResponse>> =
        flow {
            try {
                emit(Resource.Loading<SendAuthCodeResponse>())
                val response = repository.sendAuthCode(authCodeBody)
                emit(Resource.Success<SendAuthCodeResponse>(response))
            } catch (e: HttpException) {
                val gson = Gson()
                lateinit var jsonString:String;
                lateinit var response:SendAuthCodeResponse
                e.response()?.errorBody()?.let {
                    jsonString = it.string()
                    Log.e("GSON", jsonString)
                    response = gson.fromJson(jsonString, SendAuthCodeResponse::class.java)
                }
                emit(
                    Resource.Error<SendAuthCodeResponse>(
                        response.toString() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<SendAuthCodeResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<SendAuthCodeResponse>("${e.message}"))
            }
        }
}