package com.ahrorovk.testmangofzco.data.repository

import com.ahrorovk.testmangofzco.data.ApplicationApi
import com.ahrorovk.testmangofzco.domain.model.requestBody.*
import com.ahrorovk.testmangofzco.domain.model.requestResponse.*
import com.ahrorovk.testmangofzco.domain.repository.TestMangoRepository
import retrofit2.Call

class TestMangoRepositoryImpl(
    private val testMangoApi:ApplicationApi
): TestMangoRepository {
    override suspend fun getCurrentUser(token: String): UserInfoResponse = testMangoApi.getCurrentUser(token)
    override suspend fun registerUser(
        user: UserBody
    ): UserRegistrationResponse = testMangoApi.registerUser(user)

    override suspend fun sendAuthCode(
        authCodeBody: SendAuthCodeBody
    ): SendAuthCodeResponse = testMangoApi.sendAuthCode(authCodeBody)

    override suspend fun checkAuthCode(
        authCodeBody: CheckAuthCodeBody
    ): CheckAuthCodeResponse = testMangoApi.checkAuthCode( authCodeBody)

    override suspend fun updateUserInfo(
        token: String,
        updateUserInfoBody: UpdateUserInfoBody
    ): UpdateUserInfoResponse = testMangoApi.updateUserInfo(token,updateUserInfoBody)

    override suspend fun refreshToken(
        refreshTokenBody: RefreshTokenBody
    ): RefreshTokenResponse = testMangoApi.refreshToken(refreshTokenBody)
}
