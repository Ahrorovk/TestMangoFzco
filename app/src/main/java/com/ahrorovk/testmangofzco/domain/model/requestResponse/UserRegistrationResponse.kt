package com.ahrorovk.testmangofzco.domain.model.requestResponse

data class UserRegistrationResponse(
    val access_token: String,
    val refresh_token: String,
    val user_id: Int,
    val detail: Detail
)