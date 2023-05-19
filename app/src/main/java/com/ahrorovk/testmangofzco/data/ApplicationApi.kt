package com.ahrorovk.testmangofzco.data
import com.ahrorovk.testmangofzco.domain.model.requestBody.*
import com.ahrorovk.testmangofzco.domain.model.requestResponse.*
import retrofit2.http.*


interface ApplicationApi {
    @GET("api/v1/users/me/")
    suspend fun getCurrentUser(
        @Header("Authorization")token:String
    ):UserInfoResponse

    @Headers("accept: application/json", "Content-Type: application/json")
    @POST("/api/v1/users/register/")
    suspend fun registerUser(
        @Body user: UserBody
    ): UserRegistrationResponse

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(
        @Body authCodeBody: SendAuthCodeBody
    ): SendAuthCodeResponse

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(
        @Body refreshTokenBody: RefreshTokenBody
    ): RefreshTokenResponse

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body authCodeBody: CheckAuthCodeBody
    ): CheckAuthCodeResponse

    @PUT("/api/v1/users/me/")
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body updateUserInfoBody: UpdateUserInfoBody
    ): UpdateUserInfoResponse
}