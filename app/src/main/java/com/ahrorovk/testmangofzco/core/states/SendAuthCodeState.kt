package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.SendAuthCodeResponse
data class SendAuthCodeState(
    var isLoading: Boolean = false,
    var response: SendAuthCodeResponse? = null,
    val error: String = ""
)