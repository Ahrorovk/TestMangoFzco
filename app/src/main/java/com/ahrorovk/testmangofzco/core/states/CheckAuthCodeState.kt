package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.CheckAuthCodeResponse

data class CheckAuthCodeState(
    var isLoading: Boolean = false,
    var response: CheckAuthCodeResponse? = null,
    val error: String = ""
)
