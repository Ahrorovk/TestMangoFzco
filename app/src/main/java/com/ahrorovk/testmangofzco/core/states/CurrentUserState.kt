package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.UserInfoResponse

data class CurrentUserState(
    var isLoading: Boolean = false,
    var response: UserInfoResponse? = null,
    val error: String = ""
)