package com.ahrorovk.testmangofzco.core.states

import com.ahrorovk.testmangofzco.domain.model.requestResponse.RefreshTokenResponse

data class RefreshTokenState(
    val isLoading:Boolean = false,
    val response:RefreshTokenResponse? = null,
    val error:String = ""
)
