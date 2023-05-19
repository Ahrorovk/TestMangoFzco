package com.ahrorovk.testmangofzco.domain.model.requestResponse

data class CheckAuthCodeResponse(
    val access_token: String? = null,
    val is_user_exists: Boolean,
    val refresh_token: String? = null,
    val user_id: Int? = null
)