package com.ahrorovk.testmangofzco.domain.use_cases

import com.ahrorovk.testmangofzco.core.Resource
import com.ahrorovk.testmangofzco.domain.model.requestBody.RefreshTokenBody
import com.ahrorovk.testmangofzco.domain.model.requestResponse.RefreshTokenResponse
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: TestMangoRepository
) {
    operator fun invoke(refreshTokenBody: RefreshTokenBody): Flow<Resource<RefreshTokenResponse>> =
        flow {
            try {
                emit(Resource.Loading<RefreshTokenResponse>())
                val response = repository.refreshToken(refreshTokenBody)
                emit(Resource.Success<RefreshTokenResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<RefreshTokenResponse>(
                        e.localizedMessage ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<RefreshTokenResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<RefreshTokenResponse>("${e.message}"))
            }
        }
}