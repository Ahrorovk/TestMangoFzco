package com.ahrorovk.testmangofzco.domain.use_cases

import android.util.Log
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserInfoResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserRegistrationResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(token: String): Flow<Resource<UserInfoResponse>> =
        flow {
            try {
                emit(Resource.Loading<UserInfoResponse>())
                val response = repository.getCurrentUser("Bearer $token")
                emit(Resource.Success<UserInfoResponse>(response))
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
                    Resource.Error<UserInfoResponse>(
                        e.localizedMessage ?: "An unexpected error occured"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<UserInfoResponse>("Couldn't reach server. Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<UserInfoResponse>("${e.message}"))
            }
        }

}