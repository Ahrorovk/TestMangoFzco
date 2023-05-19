package com.ahrorovk.testmangofzco.domain.repository

import com.ahrorovk.testmangofzco.domain.model.requestBody.*
import com.ahrorovk.testmangofzco.domain.model.requestResponse.*
import retrofit2.Call

interface TestMangoRepository {
    suspend fun getCurrentUser(token: String): UserInfoResponse
    suspend fun registerUser( user: UserBody): UserRegistrationResponse
    suspend fun sendAuthCode(
        authCodeBody: SendAuthCodeBody
    ): SendAuthCodeResponse

    suspend fun checkAuthCode(
        authCodeBody: CheckAuthCodeBody
    ): CheckAuthCodeResponse

    suspend fun updateUserInfo(
        token: String,
        updateUserInfoBody: UpdateUserInfoBody
    ): UpdateUserInfoResponse

    suspend fun refreshToken(
        refreshTokenBody: RefreshTokenBody
    ): RefreshTokenResponse
}