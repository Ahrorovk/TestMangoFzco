package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserRegistrationResponse

data class UserRegistrationState(
    var isLoading: Boolean = false,
    var response: UserRegistrationResponse? = null,
    val error: String = ""
)