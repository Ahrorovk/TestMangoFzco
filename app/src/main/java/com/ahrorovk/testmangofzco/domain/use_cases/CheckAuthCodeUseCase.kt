package com.ahrorovk.testmangofzco.domain.use_cases

import com.ahrorovk.testmangofzco.core.Constants
import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.domain.model.requestBody.CheckAuthCodeBody
import com.ahrorovk.testmangofzco.domain.model.requestBody.RefreshTokenBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.CheckAuthCodeResponse
import com.ahrorovk.testmangofzco.domain.model.requestResponse.RefreshTokenResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(refreshTokenBody: CheckAuthCodeBody): Flow<Resource<CheckAuthCodeResponse>> =
        flow {
            try {
                emit(Resource.Loading<CheckAuthCodeResponse>())
                val response = repository.checkAuthCode(refreshTokenBody)
                emit(Resource.Success<CheckAuthCodeResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<CheckAuthCodeResponse>(
                        e.localizedMessage ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<CheckAuthCodeResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<CheckAuthCodeResponse>("${e.message}"))
            }
        }
}